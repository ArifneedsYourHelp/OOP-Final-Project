package model;

/**
 * Represents the manager with fixed login credentials.
 * This class implements the {@code User} interface and provides immutable
 * credentials for a single administrative account.
 * In a production environment, fixed credentials should be stored securely
 * (e.g., hashed and configured via external means).
 */
public class Manager implements User {

    /** The immutable username for the manager account. */
    private final String username = "admin";

    /** The immutable password for the manager account. */
    private final String password = "admin123";

    /**
     * Gets the fixed username for the manager ("admin").
     * @return The manager's username.
     */
    @Override
    public String getUsername() { return username; }

    /**
     * Gets the fixed password for the manager ("admin123").
     * @return The manager's password.
     */
    @Override
    public String getPassword() { return password; }

    /**
     * Verifies if the provided input string matches the fixed manager password.
     * @param input The password string entered for verification.
     * @return {@code true} if the input matches the fixed password, {@code false} otherwise.
     */
    @Override
    public boolean verifyPassword(String input) {
        return password.equals(input);
    }
}
