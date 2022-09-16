package main;

import main.StudentOrder;

public class StudentOrderValidator {

    public static void main(String[] args) {
        checkAll();
    }

    static void checkAll() {
        StudentOrder studentOrder = readStudentOrder();

        checkCityRegister(studentOrder);
        checkWedding(studentOrder);
        checkChildren(studentOrder);
        checkStudent(studentOrder);

        sendMailStudentOrder(studentOrder);

    }

    static StudentOrder readStudentOrder() {
        StudentOrder studentOrder = new StudentOrder();
        return studentOrder;
    }

    static void checkCityRegister(StudentOrder studentOrder) {
        System.out.println("City register check is running");
    }

    static void checkWedding(StudentOrder studentOrder) {
        System.out.println("Wedding check is running");
    }

    static void checkChildren(StudentOrder studentOrder) {
        System.out.println("Children check is running");
    }

    static void checkStudent(StudentOrder studentOrder) {
        System.out.println("Student check is running");
    }

    static void sendMailStudentOrder(StudentOrder studentOrder) {

    }
}
