package main.dao;

import main.domain.StudentOrder;
import main.exception.DaoException;

public interface StudentOrderDao {

    Long saveStudentOrder(StudentOrder so) throws DaoException;
}
