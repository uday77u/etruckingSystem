package testdata;

public class TestContext {

    private static String generatedEmail;
    private static String phone;

    public static void setGeneratedEmail(String email) {
        generatedEmail = email;
    }

    public static String getGeneratedEmail() {
        return generatedEmail;
    }

    public static void setPhone(String ph) {
        phone = ph;
    }

    public static String getPhone() {
        return phone;
    }
}
