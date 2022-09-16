package main;

import answer.AnswerChildren;
import answer.AnswerCityRegister;
import answer.AnswerStudent;
import answer.AnswerWedding;

public class StudentOrderValidator {

    public static void main(String[] args) {
        checkAll();
    }

    static void checkAll() {
        StudentOrder studentOrder = readStudentOrder();

        AnswerCityRegister answerCityRegister = checkCityRegister(studentOrder);
        AnswerWedding answerWedding = checkWedding(studentOrder);
        AnswerChildren answerChildren = checkChildren(studentOrder);
        AnswerStudent answerStudent = checkStudent(studentOrder);

        sendMailStudentOrder(studentOrder);
    }

    static StudentOrder readStudentOrder() {
        StudentOrder studentOrder = new StudentOrder();
        return studentOrder;
    }

    static AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        System.out.println("City register check is running");
        AnswerCityRegister cityRegister = new AnswerCityRegister();
        return cityRegister;
    }

    static AnswerWedding checkWedding(StudentOrder studentOrder) {
        System.out.println("Wedding check is running");
        AnswerWedding answerWedding = new AnswerWedding();
        return answerWedding;
    }

    static AnswerChildren checkChildren(StudentOrder studentOrder) {
        System.out.println("Children check is running");
        AnswerChildren answerChildren = new AnswerChildren();
        return answerChildren;
    }

    static AnswerStudent checkStudent(StudentOrder studentOrder) {
        System.out.println("Student check is running");
        AnswerStudent answerStudent = new AnswerStudent();
        return answerStudent;
    }

    static void sendMailStudentOrder(StudentOrder studentOrder) {

    }
}
