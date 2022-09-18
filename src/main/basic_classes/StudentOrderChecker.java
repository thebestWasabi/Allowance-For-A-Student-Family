package main.basic_classes;

import main.answer.AnswerChildren;
import main.answer.AnswerCityRegister;
import main.answer.AnswerStudent;
import main.answer.AnswerWedding;
import main.domain.StudentOrder;
import main.mail.MailSender;
import main.validator.ChildrenValidator;
import main.validator.CityRegisterValidator;
import main.validator.StudentValidator;
import main.validator.WeddingValidator;

public class StudentOrderChecker {

    private CityRegisterValidator cityRegisterValidator;
    private WeddingValidator weddingValidator;
    private ChildrenValidator childrenValidator;
    private StudentValidator studentValidator;
    private MailSender mailSender;


    public StudentOrderChecker() {
        cityRegisterValidator = new CityRegisterValidator();
        weddingValidator = new WeddingValidator();
        childrenValidator = new ChildrenValidator();
        studentValidator = new StudentValidator();
        mailSender = new MailSender();
    }


    public static void main(String[] args) {
        StudentOrderChecker soChecker = new StudentOrderChecker();
        soChecker.checkAll();
    }


    public void checkAll() {
        StudentOrder[] soArray = readStudentOrders();
        for (StudentOrder studentOrder : soArray) {
            System.out.println();
            checkOneOrder(studentOrder);
        }
    }


    public StudentOrder[] readStudentOrders() {
        StudentOrder[] soArray = new StudentOrder[3];
        for (int i = 0; i < soArray.length; i++) {
            soArray[i] = SaveStudentOrder.buildStudentOrder(i);
        }
        return soArray;
    }


    public void checkOneOrder(StudentOrder studentOrder) {
        AnswerCityRegister answerCityRegister = checkCityRegister(studentOrder);
        AnswerWedding answerWedding = checkWedding(studentOrder);
        AnswerChildren answerChildren = checkChildren(studentOrder);
        AnswerStudent answerStudent = checkStudent(studentOrder);
        sendMailStudentOrder(studentOrder);
    }


    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        return cityRegisterValidator.checkCityRegister(studentOrder);
    }

    public AnswerWedding checkWedding(StudentOrder studentOrder) {
        return weddingValidator.checkWedding(studentOrder);
    }

    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        return childrenValidator.checkChildren(studentOrder);
    }

    public AnswerStudent checkStudent(StudentOrder studentOrder) {
        return studentValidator.checkStudent(studentOrder);
    }

    public void sendMailStudentOrder(StudentOrder studentOrder) {
        mailSender.sendMailStudentOrder(studentOrder);
    }
}
