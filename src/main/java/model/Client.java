package model;

/**
 * Represents a client who can log in and browse movies.
 */
public class Client implements User {
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

    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email format.");
        this.email = email;
    }

    @Override
    public boolean verifyPassword(String input) {
        return password.equals(input);
    }
}