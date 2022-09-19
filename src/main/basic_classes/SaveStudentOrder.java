package main.basic_classes;

import main.domain.Adult;
import main.domain.Person;
import main.domain.StudentOrder;

public class SaveStudentOrder {

    public static void main(String[] args) {
//        StudentOrder so = new StudentOrder();
//        long ans = saveStudentOrder(so);
//        System.out.println(ans);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.print("Сохранение студенчиской заявки");
        return answer;
    }

    static StudentOrder buildStudentOrder(long id) {
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderId(id);

        Adult husband = new Adult("Хамзин", "Максим", "Альбертович", null);
        Adult wife = new Adult("Дарья", "Хамзина", "Сергеевна", null);

        return studentOrder;
    }
}
