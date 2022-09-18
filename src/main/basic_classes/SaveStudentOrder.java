package main.basic_classes;

import main.domain.Adult;
import main.domain.Person;
import main.domain.StudentOrder;

public class SaveStudentOrder {

    public static void main(String[] args) {
        buildStudentOrder();
//        StudentOrder so = new StudentOrder();
//        long ans = saveStudentOrder(so);
//        System.out.println(ans);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.print("Сохранение студенчиской заявки");
        return answer;
    }

    static StudentOrder buildStudentOrder() {
        StudentOrder studentOrder = new StudentOrder();
        Adult husband = new Adult();
        husband.setGivenName("Максим");
        husband.setSurName("Хамзин");
        husband.setPassportNumber("123456");
        studentOrder.setHusband(husband);

        String ans = husband.getPersonString();
        System.out.println(ans);

        return studentOrder;
    }
}
