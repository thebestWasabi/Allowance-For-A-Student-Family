package main.basic_classes;

import main.answer.AnswerChildren;
import main.answer.AnswerCityRegister;
import main.answer.AnswerStudent;
import main.answer.AnswerWedding;
import main.domain.StudentOrder;
import main.mail.MailSender;
import main.validator.*;

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
        return srv1.checkCityRegister(studentOrder);
    }

    static AnswerWedding checkWedding(StudentOrder studentOrder) {
        WeddingValidator wd = new WeddingValidator();
        return wd.checkWedding(studentOrder);
    }

    static AnswerChildren checkChildren(StudentOrder studentOrder) {
        ChildrenValidator chv = new ChildrenValidator();
        return chv.checkChildren(studentOrder);
    }

    static AnswerStudent checkStudent(StudentOrder studentOrder) {
        StudentValidator sv = new StudentValidator();
        return sv.checkStudent(studentOrder);
    }

    static void sendMailStudentOrder(StudentOrder studentOrder) {
        new MailSender().sendMailStudentOrder(studentOrder);
    }
}
