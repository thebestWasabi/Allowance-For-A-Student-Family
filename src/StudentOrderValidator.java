public class StudentOrderValidator {

    public static void main(String[] args) {
        checkAll();
    }

    static void checkAll() {
        checkCityRegister();
        checkWedding();
        checkChildren();
        checkStudent();
    }

    static void checkCityRegister() {
        System.out.println("City register check is running");
    }

    static void checkWedding() {
        System.out.println("Wedding check is running");
    }

    static void checkChildren() {
        System.out.println("Children check is running");
    }

    static void checkStudent() {
        System.out.println("Student check is running");
    }
}
