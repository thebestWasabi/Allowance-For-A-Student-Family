package main;

import answer.AnswerChildren;
import answer.AnswerCityRegister;
import answer.AnswerStudent;
import answer.AnswerWedding;
import validator.*;

public class StudentOrderValidator {

    public static void main(String[] args) {
        checkAll();
    }

    static void checkAll() {

        while (true) {
            StudentOrder studentOrder = readStudentOrder();

            if (studentOrder == null) {
                break;
            } else {
                AnswerCityRegister answerCityRegister = checkCityRegister(studentOrder);
                if (!answerCityRegister.success) {
//                    continue;
                    break;
                }
                AnswerWedding answerWedding = checkWedding(studentOrder);
                AnswerChildren answerChildren = checkChildren(studentOrder);
                AnswerStudent answerStudent = checkStudent(studentOrder);

                sendMailStudentOrder(studentOrder);
                studentOrder = readStudentOrder();
            }
        }
    }


    static StudentOrder readStudentOrder() {
        StudentOrder studentOrder = new StudentOrder();
        return studentOrder;
    }

    static AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        CityRegisterValidator srv1 = new CityRegisterValidator();
        srv1.setHostName("Host1");
        srv1.setLogin("Login1");
        srv1.setPassword("Password1");

        AnswerCityRegister answer1 = srv1.checkCityRegister(studentOrder);
        return answer1;
    }

    static AnswerWedding checkWedding(StudentOrder studentOrder) {
        return WeddingValidator.checkWedding(studentOrder);
    }

    static AnswerChildren checkChildren(StudentOrder studentOrder) {
        return ChildrenValidator.checkChildren(studentOrder);
    }

    static AnswerStudent checkStudent(StudentOrder studentOrder) {
        return StudentValidator.checkStudent(studentOrder);
    }

    static void sendMailStudentOrder(StudentOrder studentOrder) {
        SendMailValidator.sendMailStudentOrder(studentOrder);
    }
}
