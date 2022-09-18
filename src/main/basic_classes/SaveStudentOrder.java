package main.basic_classes;

import main.domain.StudentOrder;

public class SaveStudentOrder {

    public static void main(String[] args) {
        StudentOrder studentOrder1 = new StudentOrder();
        studentOrder1.setHusbandFirstName("Максим");
        studentOrder1.setHusbandLastName("Хамзин");
        studentOrder1.setWifeFirstName("Дарья");
        studentOrder1.setWifeLastName("Хамзина");

        long answer1 = saveStudentOrder(studentOrder1);
        System.out.println(answer1);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.print("Сохранение студенчиской заявки: " + studentOrder.getHusbandLastName());
        return answer;
    }
}
