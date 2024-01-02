package edu.najah.cap.data;

import java.util.logging.Level;
import java.util.logging.Logger;

//Singleton
public class UserData implements Database {

    private static volatile UserData instance;
    private static final Logger LOGGER = Logger.getLogger(UserData.class.getName());

    private UserData() {
        // private constructor to prevent instantiation
    }

    //Overriding of Database class
    public void connect() {
        System.out.println("Connecting to FakeDatabase!!");
    }

    //Overriding of Database class
    public void disconnect() {
        System.out.println("Disconnected from the FakeDatabase.");
    }

    // implementation uses the Singleton to ensure a single connection for each user
    public static UserData getInstance() {
        if (instance == null) {
            synchronized (UserData.class) {
                if (instance == null) {
                    instance = new UserData();
                }
            }
        }
        return instance;
    }

    public MergeObject getMergeObjectForUser(String userName, FakeDataBase DB) {
        try {
            return FakeDataBase.UserInDatabase(userName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving merge object for user: " + userName, e);
            // Handle or rethrow the exception as needed
            return null; // or throw e; depending on how you want to handle the exception
        }
    }
}
