package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client who can log in and browse movies.
 */
public class Client implements User {

    private static final List<Client> registeredClients = new ArrayList<>();


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
        registeredClients.clear();

        registeredClients.add(createClientWithoutValidation("Alice Martin", "pass123", "alice@email.com"));
        registeredClients.add(createClientWithoutValidation("Bob Chen", "secure456", "bob@email.com"));
        registeredClients.add(createClientWithoutValidation("Clara Dupont", "cinema789", "clara@email.com"));
        registeredClients.add(createClientWithoutValidation("David Singh", "ticket321", "david@email.com"));
        registeredClients.add(createClientWithoutValidation("client", "client123", "test@example.com"));
    }

    public static List<Client> getRegisteredClientsSnapshot() {
        return new ArrayList<>(registeredClients);
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