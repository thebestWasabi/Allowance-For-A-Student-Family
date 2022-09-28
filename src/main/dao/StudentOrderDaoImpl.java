package main.dao;

import main.config.Config;
import main.domain.Address;
import main.domain.Adult;
import main.domain.StudentOrder;
import main.domain.StudentOrderStatus;
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

            // Husband
            Adult husband = so.getHusband();
            statement.setString(3, husband.getSurName());
            statement.setString(4, husband.getGivenName());
            statement.setString(5, husband.getPatronymic());
            statement.setDate(6, java.sql.Date.valueOf(husband.getDateOfBirth()));
            statement.setString(7, husband.getPassportSeries());
            statement.setString(8, husband.getPassportNumber());
            statement.setDate(9, java.sql.Date.valueOf(husband.getIssueDate()));
            statement.setLong(10, so.getHusband().getIssueDepartment().getOfficeId());
            Address h_address = husband.getAddress();
            statement.setString(11, h_address.getPostCode());
            statement.setLong(12, h_address.getStreet().getStreetCode());
            statement.setString(13, h_address.getBuilding());
            statement.setString(14, h_address.getExtension());
            statement.setString(15, h_address.getApartment());

            // Wife
            Adult wife = so.getWife();
            statement.setString(16, wife.getSurName());
            statement.setString(17, wife.getGivenName());
            statement.setString(18, wife.getPatronymic());
            statement.setDate(19, java.sql.Date.valueOf(wife.getDateOfBirth()));
            statement.setString(20, wife.getPassportSeries());
            statement.setString(21, wife.getPassportNumber());
            statement.setDate(22, java.sql.Date.valueOf(wife.getIssueDate()));
            statement.setLong(23, so.getWife().getIssueDepartment().getOfficeId());
            Address w_address = wife.getAddress();
            statement.setString(24, w_address.getPostCode());
            statement.setLong(25, w_address.getStreet().getStreetCode());
            statement.setString(26, w_address.getBuilding());
            statement.setString(27, w_address.getExtension());
            statement.setString(28, w_address.getApartment());

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

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }

        return result;
    }
}
