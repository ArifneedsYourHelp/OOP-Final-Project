package model;

/**
 * Represents a client who can log in and browse movies.
 * Implements the {@code Model.User} interface, providing core authentication and identity methods.
 * Note: In a real-world application, the password should be securely hashed rather than stored as a plain string.
 */
public class Client implements Model.User {
    private String username;
    private String password;
    private String email;

    /**
     * Creates a new client account.
     *
     * @param username chosen username
     * @param password chosen password
     * @param email client email address
     */
    public Client(String username, String password, String email) {

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be empty");

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be empty");

        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email format.");

        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Gets the username of the client.
     * @return The client's username.
     */
    @Override
    public String getUsername() { return username; }

    /**
     * Gets the password of the client.
     * @return The client's password (unhashed string).
     */
    @Override
    public String getPassword() { return password; }

    /**
     * Gets the email address of the client.
     * @return The client's email address.
     */
    public String getEmail() { return email; }

    /**
     * Sets a new email address for the client.
     * @param email The new email address.
     * @throws IllegalArgumentException if the email format is invalid (does not contain '@').
     */
    public void setEmail(String email) {
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email format.");
        this.email = email;
    }

    /**
     * Verifies if the provided input string matches the client's stored password.
     * @param input The password string entered by the user.
     * @return {@code true} if the input matches the stored password, {@code false} otherwise.
     */
    @Override
    public boolean verifyPassword(String input) {
        return password.equals(input);
    }
}
