package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client who can log in and browse movies.
 */
public class Client implements User {

    private static final List<Client> registeredClients = new ArrayList<>();

    static {
        try {
            // Create and register a sample client automatically
            Client sampleClient = new Client("client", "client123", "test@example.com");
            registerClient(sampleClient);
            System.out.println("Sample client 'testuser' pre-registered for testing.");
        } catch (IllegalArgumentException e) {
            // This usually won't happen unless the static data is invalid
            System.err.println("Failed to pre-register sample client: " + e.getMessage());
        }
    }

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

        if (findClientByUsername(username) != null) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be empty");

        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email format.");

        this.username = username;
        this.password = password;
        this.email = email;

        registeredClients.add(this);
    }

    public static Client findClientByUsername(String username) {
        for (Client client : registeredClients) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    public static void registerClient(Client client) {
        if (client != null) {
            registeredClients.add(client);
        }
    }

    /**
     * Loads sample clients into the system for testing/demo purposes.
     * This method clears existing clients and loads predefined sample data.
     */
    public static void loadSampleClients() {
        // Clear existing clients except the default one
        registeredClients.clear();

        // Register sample clients from SampleDataHelper
        try {
            // Alice Martin
            Client alice = createClientWithoutValidation("Alice Martin", "pass123", "alice@email.com");
            registeredClients.add(alice);

            // Bob Chen
            Client bob = createClientWithoutValidation("Bob Chen", "secure456", "bob@email.com");
            registeredClients.add(bob);

            // Clara Dupont
            Client clara = createClientWithoutValidation("Clara Dupont", "cinema789", "clara@email.com");
            registeredClients.add(clara);

            // David Singh
            Client david = createClientWithoutValidation("David Singh", "ticket321", "david@email.com");
            registeredClients.add(david);

            // Also add the default test client
            Client testClient = createClientWithoutValidation("client", "client123", "test@example.com");
            registeredClients.add(testClient);

            System.out.println("Sample clients loaded: Alice Martin, Bob Chen, Clara Dupont, David Singh, client");
        } catch (Exception e) {
            System.err.println("Error loading sample clients: " + e.getMessage());
        }
    }

    /**
     * Creates a client without duplicate validation (for sample data loading).
     */
    private static Client createClientWithoutValidation(String username, String password, String email) {
        Client client = new Client();
        client.username = username;
        client.password = password;
        client.email = email;
        return client;
    }

    /**
     * Private constructor for creating clients without validation (used internally).
     */
    private Client() {
        // Empty constructor for internal use only
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