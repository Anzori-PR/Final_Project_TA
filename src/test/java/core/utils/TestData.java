package core.utils;

public final class TestData {
    private TestData() {}

    public static String uniqueEmail() {
        return "user_test_" + System.currentTimeMillis() + "@gmail.com";
    }
}