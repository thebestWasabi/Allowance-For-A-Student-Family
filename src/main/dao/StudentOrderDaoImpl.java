package main.dao;

import main.config.Config;
import main.domain.*;
import main.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;

public class StudentOrderDaoImpl implements StudentOrderDao {

    private static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date, h_sur_name," +
            "h_given_name, h_patronymic, h_date_of_birth, h_passport_series," +
            "h_passport_number, h_passport_date, h_passport_office_id, h_post_index," +
            "h_street_code, h_building, h_extension, h_apartment, w_sur_name," +
            "w_given_name, w_patronymic, w_date_of_birth, w_passport_series," +
            "w_passport_number, w_passport_date, w_passport_office_id, w_post_index," +
            "w_street_code, w_building, w_extension, w_apartment, certificate_id," +
            "register_office_id, marriage_date)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String INSERT_CHILD = "INSERT INTO jc_student_child(" +
            "student_order_id, ch_sur_name, ch_given_name, ch_patronymic," +
            "ch_date_of_birth, ch_certificate_number, ch_certificate_date, ch_register_office_id," +
            "ch_post_index, ch_street_code, ch_building, ch_extension, ch_apartment)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


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

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

            // Header
            statement.setInt(1, StudentOrderStatus.START.ordinal());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

            // Husband and Wife
            setParamsForAdult(statement, 3, so.getHusband());
            setParamsForAdult(statement, 16, so.getWife());

            // Marriage
            statement.setString(29, so.getMarriageCertificateId());
            statement.setLong(30, so.getMarriageOffice().getOfficeId());
            statement.setDate(31, java.sql.Date.valueOf(so.getMarriageDate()));

            statement.executeUpdate();

            ResultSet gKeysRs = statement.getGeneratedKeys();
            if (gKeysRs.next()) {
                result = gKeysRs.getLong(1);
            }
            gKeysRs.close();

            saveChildren(con, so, result);

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    private void saveChildren(Connection con, StudentOrder so, Long soId) throws SQLException {

        try (PreparedStatement statement = con.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                statement.setLong(1, soId);
                setParamsForChild(statement ,child);
                statement.executeUpdate();
            }
        }
    }

    private static void setParamsForAdult(PreparedStatement statement, int start, Adult adult) throws SQLException {
        setParamsForPerson(statement, start, adult);
        statement.setString(start + 4, adult.getPassportSeries());
        statement.setString(start + 5, adult.getPassportNumber());
        statement.setDate(start + 6, Date.valueOf(adult.getIssueDate()));
        statement.setLong(start + 7, adult.getIssueDepartment().getOfficeId());
        setParamsForAddress(statement, start + 8, adult);
    }

    private void setParamsForChild(PreparedStatement statement, Child child) throws SQLException {
        setParamsForPerson(statement, 2, child);
        statement.setString(6, child.getCertificateNumber());
        statement.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
        statement.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(statement, 9, child);
    }

    private static void setParamsForPerson(PreparedStatement statement, int start, Person person) throws SQLException {
        statement.setString(start, person.getSurName());
        statement.setString(start + 1, person.getGivenName());
        statement.setString(start + 2, person.getPatronymic());
        statement.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
    }

    private static void setParamsForAddress(PreparedStatement statement, int start, Person person) throws SQLException {
        Address adult_address = person.getAddress();
        statement.setString(start, adult_address.getPostCode());
        statement.setLong(start + 1, adult_address.getStreet().getStreetCode());
        statement.setString(start + 2, adult_address.getBuilding());
        statement.setString(start + 3, adult_address.getExtension());
        statement.setString(start + 4, adult_address.getApartment());
    }
}
