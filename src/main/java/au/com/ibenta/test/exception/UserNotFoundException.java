package au.com.ibenta.test.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String id) {
        System.err.printf("User with id %s not found. Verify your id number and try again.", id);
    }
}
