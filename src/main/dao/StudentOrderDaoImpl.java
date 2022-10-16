package main.dao;

import main.config.Config;
import main.domain.*;
import main.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentOrderDaoImpl implements StudentOrderDao {

    private static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date, h_sur_name," +
            "h_given_name, h_patronymic, h_date_of_birth, h_passport_series," +
            "h_passport_number, h_passport_date, h_passport_office_id, h_post_index," +
            "h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number," +
            "w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_series," +
            "w_passport_number, w_passport_date, w_passport_office_id, w_post_index," +
            "w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number, " +
            "certificate_id, register_office_id, marriage_date)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String INSERT_CHILD = "INSERT INTO jc_student_child(" +
            "student_order_id, ch_sur_name, ch_given_name, ch_patronymic," +
            "ch_date_of_birth, ch_certificate_number, ch_certificate_date, ch_register_office_id," +
            "ch_post_index, ch_street_code, ch_building, ch_extension, ch_apartment)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ORDERS = "SELECT so.*, ro.r_office_area_id, ro.r_office_name," +
            "po_h.p_office_area_id as h_p_office_area_id, po_h.p_office_name as h_p_office_name, " +
            "po_w.p_office_area_id as w_p_office_area_id, po_w.p_office_name as w_p_office_name " +
            "FROM jc_student_order so " +
            "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
            "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
            "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
            "WHERE student_order_status = ? ORDER BY student_order_date; ";

    public static final String SELECT_CHILD = "SELECT soch.*, ro.r_office_area_id, ro.r_office_name " +
            "FROM jc_student_child soch " +
            "INNER JOIN jc_register_office ro ON ro.r_office_id = soch.ch_register_office_id " +
            "WHERE soch.student_order_id IN ";


    // TODO: 28.09.2022 refactoring - make one method
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return connection;
    }

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {
        long result = -1L;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

            conn.setAutoCommit(false);
            try {
                // Header
                stmt.setInt(1, StudentOrderStatus.START.ordinal());
                stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
                // Husband and Wife
                setParamsForAdult(stmt, 3, so.getHusband());
                setParamsForAdult(stmt, 18, so.getWife());
                // Marriage
                stmt.setString(33, so.getMarriageCertificateId());
                stmt.setLong(34, so.getMarriageOffice().getOfficeId());
                stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));

                stmt.executeUpdate();

                ResultSet gKeysRs = stmt.getGeneratedKeys();
                if (gKeysRs.next()) {
                    result = gKeysRs.getLong(1);
                }
                gKeysRs.close();

                saveChildren(conn, so, result);
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    private void saveChildren(Connection connect, StudentOrder so, Long soId) throws SQLException {
        try (PreparedStatement stmt = connect.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                stmt.setLong(1, soId);
                setParamsForChild(stmt, child);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private static void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start + 4, adult.getPassportSeries());
        stmt.setString(start + 5, adult.getPassportNumber());
        stmt.setDate(start + 6, Date.valueOf(adult.getPassportIssueDate()));
        stmt.setLong(start + 7, adult.getPassportDepartment().getOfficeId());
        setParamsForAddress(stmt, start + 8, adult);
        stmt.setLong(start + 13, adult.getUniversity().getUniversityId());
        stmt.setString(start + 14, adult.getStudentId());
    }

    private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException {
        setParamsForPerson(stmt, 2, child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
        stmt.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, 9, child);
    }

    private static void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
        stmt.setString(start, person.getSurName());
        stmt.setString(start + 1, person.getGivenName());
        stmt.setString(start + 2, person.getPatronymic());
        stmt.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
    }

    private static void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
        Address adult_address = person.getAddress();
        stmt.setString(start, adult_address.getPostCode());
        stmt.setLong(start + 1, adult_address.getStreet().getStreetCode());
        stmt.setString(start + 2, adult_address.getBuilding());
        stmt.setString(start + 3, adult_address.getExtension());
        stmt.setString(start + 4, adult_address.getApartment());
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ORDERS)) {

            statement.setInt(1, StudentOrderStatus.START.ordinal());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentOrder studentOrder = new StudentOrder();

                fillStudentOrder(resultSet, studentOrder);
                fillMarriage(resultSet, studentOrder);

                Adult husband = fillAdult(resultSet, "h_");
                Adult wife = fillAdult(resultSet, "w_");
                studentOrder.setHusband(husband);
                studentOrder.setWife(wife);

                result.add(studentOrder);
            }
            findChildren(conn, result);

            resultSet.close();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    private void findChildren(Connection connection, List<StudentOrder> result) throws SQLException {
        String collect = "(" + result.stream().map(so -> String.valueOf(so.getStudentOrderId()))
                .collect(Collectors.joining(",")) + ")";

        Map<Long, StudentOrder> maps = result.stream().collect(Collectors
                .toMap(studentOrder -> studentOrder.getStudentOrderId(), studentOrder -> studentOrder));

        try (PreparedStatement statement = connection.prepareStatement(SELECT_CHILD + collect)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Child ch = fillChild(resultSet);
                StudentOrder studentOrder = maps.get(resultSet.getLong("student_order_id"));
                studentOrder.addChild(ch);
            }
        }
    }

    private Adult fillAdult(ResultSet rs, String prefix) throws SQLException {
        Adult adult = new Adult();
        adult.setSurName(rs.getString(prefix + "sur_name"));
        adult.setGivenName(rs.getString(prefix + "given_name"));
        adult.setPatronymic(rs.getString(prefix + "patronymic"));
        adult.setDateOfBirth(rs.getDate(prefix + "date_of_birth").toLocalDate());
        adult.setPassportSeries(rs.getString(prefix + "passport_series"));
        adult.setPassportNumber(rs.getString(prefix + "passport_number"));
        adult.setPassportIssueDate(rs.getDate(prefix + "passport_date").toLocalDate());

        Long poId = rs.getLong(prefix + "passport_office_id");
        String poAreaId = rs.getString(prefix + "p_office_area_id");
        String poName = rs.getString(prefix + "p_office_name");
        PassportOffice po = new PassportOffice(poId, poAreaId, poName);
        adult.setPassportDepartment(po);

        Address address = new Address();
        address.setPostCode(rs.getString(prefix + "post_index"));
        Street street = new Street(rs.getLong(prefix + "street_code"), "");
        address.setStreet(street);
        address.setBuilding(rs.getString(prefix + "building"));
        address.setExtension(rs.getString(prefix + "extension"));
        address.setApartment(rs.getString(prefix + "apartment"));
        adult.setAddress(address);

        University university = new University(rs.getLong(prefix + "university_id"), "");
        adult.setUniversity(university);
        adult.setStudentId(rs.getString(prefix + "student_number"));

        return adult;
    }

    private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException {
        so.setStudentOrderId(rs.getLong("student_order_id"));
        so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));
        so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
    }

    private void fillMarriage(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setMarriageCertificateId(resultSet.getString("certificate_id"));
        studentOrder.setMarriageDate(resultSet.getDate("marriage_date").toLocalDate());

        Long roId = resultSet.getLong("register_office_id");
        String areaId = resultSet.getString("r_office_area_id");
        String name = resultSet.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(roId, areaId, name);
        studentOrder.setMarriageOffice(ro);
    }

    private Child fillChild(ResultSet rs) throws SQLException {
        String surName = rs.getString("ch_sur_name");
        String givenName = rs.getString("ch_given_name");
        String patronymic = rs.getString("ch_patronymic");
        LocalDate dateOfBirth = rs.getDate("ch_date_of_birth").toLocalDate();

        Child child = new Child(surName, givenName, patronymic, dateOfBirth);

        child.setCertificateNumber(rs.getString("ch_certificate_number"));
        child.setIssueDate(rs.getDate("ch_certificate_date").toLocalDate());

        Long registerOfficeId = rs.getLong("ch_register_office_id");
        String registerOfficeAreaId = rs.getString("r_office_area_id");
        String registerOfficeName = rs.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(registerOfficeId, registerOfficeAreaId, registerOfficeName);
        child.setIssueDepartment(ro);

        Address address = new Address();
        address.setPostCode(rs.getString("ch_post_index"));
        Street street = new Street(rs.getLong("ch_street_code"), "");
        address.setStreet(street);
        address.setBuilding(rs.getString("ch_building"));
        address.setExtension(rs.getString("ch_extension"));
        address.setApartment(rs.getString("ch_apartment"));
        child.setAddress(address);

        return child;
    }
}
