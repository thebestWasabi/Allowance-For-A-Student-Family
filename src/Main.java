public class Main {

    public static void main(String[] args) {
        StudentOrder studentOrder1 = new StudentOrder();
        studentOrder1.setHusbandFirstName("Максим");
        studentOrder1.setHusbandLastName("Хамзин");
        studentOrder1.setWifeFirstName("Дарья");
        studentOrder1.setWifeLastName("Хамзина");

        StudentOrder studentOrder2 = new StudentOrder();
        studentOrder2.setHusbandFirstName("Андейр");
        studentOrder2.setHusbandLastName("Кувшинов");
        studentOrder2.setWifeFirstName("Карина");
        studentOrder2.setWifeLastName("Кувшинова");

        long answer1 = saveStudentOrder(studentOrder1);
        System.out.println(answer1);
        long answer2 = saveStudentOrder(studentOrder2);
        System.out.println(answer2);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.print("Сохранение студенчиской заявки: " + studentOrder.getHusbandLastName());
        return answer;
    }
}
