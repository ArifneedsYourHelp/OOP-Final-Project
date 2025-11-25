package Model;

/**
 * Represents the manager with fixed login credentials.
 */
public class Manager implements User {
    private final String username = "admin";
    private final String password = "admin123";

    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

    @Override
    public boolean verifyPassword(String input) {
        return password.equals(input);
    }
}
