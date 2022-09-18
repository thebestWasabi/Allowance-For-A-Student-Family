package main.basic_classes;

import main.domain.Adult;
import main.domain.Person;
import main.domain.StudentOrder;

public class SaveStudentOrder {

    public static void main(String[] args) {
        StudentOrder studentOrder1 = new StudentOrder();
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.print("Сохранение студенчиской заявки: ");
        return answer;
    }

    static StudentOrder buildStudentOrder() {
        StudentOrder studentOrder = new StudentOrder();
        Adult husband = new Adult();
        husband.setGivenName("Максим");
        studentOrder.setHusband(husband);

        return studentOrder;
    }
}
