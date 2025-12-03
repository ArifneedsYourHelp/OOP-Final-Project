package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client who can log in and browse movies.
 * Implements the {@code User} interface, providing core authentication and identity methods.
 * Note: In a real-world application, the password should be securely hashed rather than stored as a plain string.
 */
public class Client implements User {

    /** A static list holding all registered client accounts. This acts as the in-memory 'database'. */
    private static final List<Client> registeredClients = new ArrayList<>();

    /**
     * Static initializer block used to set up initial data.
     * Registers a sample client automatically for testing purposes upon class loading.
     */
    static {
        try {
            // Create and register a sample client with username 'client' and password 'client123'
            Client sampleClient = new Client("client", "client123", "test@example.com");
            // The constructor already validates and sets fields, but we must add it to the static list.
            if (findClientByUsername(sampleClient.getUsername()) == null) {
                registeredClients.add(sampleClient);
            }
            System.out.println("Sample client 'client' pre-registered for testing.");
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to pre-register sample client: " + e.getMessage());
        }
    }

    // --- Private Fields ---
    private String username;
    private String password;
    private String email;

    // --- Constructor ---

    /**
     * Creates a new client account.
     * Performs basic validation on the provided credentials before instantiation.
     *
     * @param username chosen unique username.
     * @param password chosen password.
     * @param email client email address, must contain '@'.
     * @throws IllegalArgumentException if the username or password fields are empty, or if the email format is invalid.
     */
    public Client(String username, String password, String email) {

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be empty");

        // NOTE: A constructor for a new client should typically check for username uniqueness here
        // if it intends to immediately register the user, but we'll follow the existing
        // code structure where the calling controller handles uniqueness validation
        // using findClientByUsername before calling the constructor.

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be empty");

        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email format.");

        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Registers a new client by adding them to the static list of registered clients.
     * This method is used by the SignUpController.
     * @param newClient The {@code Client} object to register.
     */
    public static void registerClient(Client newClient) {
        if (newClient != null && findClientByUsername(newClient.getUsername()) == null) {
            registeredClients.add(newClient);
        }
    }

    /**
     * Searches the list of registered clients for a client with the given username.
     * This method is used by the LoginController for authentication.
     * @param username the username to search for.
     * @return the {@code Client} object if found, or {@code null} otherwise.
     */
    public static Client findClientByUsername(String username) {
        for (Client client : registeredClients) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    // --- Interface Methods (User) ---

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
     * Verifies if the provided input string matches the client's stored password.
     * @param input The password string entered by the user.
     * @return {@code true} if the input matches the stored password, {@code false} otherwise.
     */
    @Override
    public boolean verifyPassword(String input) {
        return password.equals(input);
    }

    // --- Client Specific Methods ---

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
}