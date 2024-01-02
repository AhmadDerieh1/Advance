package edu.najah.cap.data;
import edu.najah.cap.iam.UserType;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FakeDataBase implements Database{
    private static final Logger LOGGER = Logger.getLogger(FakeDataBase.class.getName());

    private DataFacade dataFacade;
    //This to follow the put values in map & avoid Duplicate User name
    private Set<String> addedUserNames;
      // |  Type   |  Obj,Obj,Obj,...  |
    public static Map<UserType, List<MergeObject>> DB;

    public FakeDataBase(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
        this.addedUserNames = new HashSet<>();
         DB = new HashMap<>();
        try {
            initializeFakeData();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing fake data", e);
            // Handle or log the exception as needed
        }
    }

    private void initializeFakeData() {
        for (int i = 0; i < 100; i++) {
            try {
                String userName = "user" + i;
                dataFacade.getMergedData(userName);
                Map<UserType, MergeObject> mergeObjectUser = dataFacade.getMergeObjectMap();
                mergeValues(DB, mergeObjectUser, addedUserNames);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error processing user" + i, e);
                // Handle or log the exception as needed
            }
        };
    }

    public boolean isUserInDatabase(String userName) {
        return isUserInDatabase(userName, DB);
    }

    private boolean isUserInDatabase(String userName, Map<UserType, List<MergeObject>> DB) {
        for (List<MergeObject> mergeObjects : DB.values()) {
            for (MergeObject mergeObject : mergeObjects) {
                if (mergeObject.getUserProfile().getUserName().equals(userName)) {
                    return true;
                }
            }
        }
        return false;
    }

public static MergeObject UserInDatabase(String userName) {
        for (List<MergeObject> mergeObjects : DB.values()) {
            for (MergeObject mergeObject : mergeObjects) {
                if (mergeObject.getUserProfile().getUserName().equals(userName)) {
                    return mergeObject;
                }
            }
        }
        return null;
    }
   

    public void mergeValues(Map<UserType, List<MergeObject>> targetMap,
     Map<UserType, MergeObject> sourceMap, Set<String> addedUserNames) {
        LOGGER.info("Merging values into the database");
        for (Map.Entry<UserType, MergeObject> entry : sourceMap.entrySet()) {
            try {
                UserType key = entry.getKey();
                MergeObject value = entry.getValue();

                if (!addedUserNames.contains(value.getUserProfile().getUserName())) {
                    addedUserNames.add(value.getUserProfile().getUserName());

                    if (targetMap.containsKey(key)) {
                        targetMap.get(key).add(value);
                    } else {
                        List<MergeObject> values = new ArrayList<>();
                        values.add(value);
                        targetMap.put(key, values);
                    }
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error merging values", e);
                // Handle or log the exception as needed
            }
        }
    }
//To Print DB
    public void printAllUserData() {
        for (Map.Entry<UserType, List<MergeObject>> entry : DB.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: ");
            for (MergeObject mergeObject : entry.getValue()) {
                System.out.println("User Profile: " + mergeObject.getUserProfile().getUserName());
                //...
            }
               System.out.println("___________________");
        }
    }

   //Overriding of Database class
     public void connect(){
        System.out.println("Connecting to FakeDatabase!!");
    }
    //Overriding of Database class
    public void disconnect() {
        System.out.println("Disconnected from the FakeDatabase.");
    }
}


