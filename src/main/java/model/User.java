package Model;
/**
 * Represents a generic user of the cinema system.
 * Provides required behavior for all user types.
 */

public interface User {



        /**
         * @return the username of the user
         */
        String getUsername();

        /**
         * @return the stored password
         */
        String getPassword();

        /**
         * Checks if a given password matches the stored password.
         *
         * @param input the entered password
         * @return true if matches
         */
        boolean verifyPassword(String input);

}
