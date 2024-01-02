package edu.najah.cap.data;
import edu.najah.cap.iam.UserType;

import java.util.*;

public class FakeDataBase implements Database{
    private DataFacade dataFacade;
    //This to follow the put values in map & avoid Duplicate User name
    private Set<String> addedUserNames;
      // |  Type   |  Obj,Obj,Obj,...  |
    public static Map<UserType, List<MergeObject>> DB;

    public FakeDataBase(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
        this.addedUserNames = new HashSet<>();
         DB = new HashMap<>();
        initializeFakeData();
    }

    private void initializeFakeData() {
        for (int i = 0; i < 100; i++) {
            String userName = "user" + i;
            dataFacade.getMergedData(userName);
            //|  Type   |  Obj  |
            Map<UserType, MergeObject> mergeObjectUser = dataFacade.getMergeObjectMap();
// mergeMap opration
            mergeValues(DB, mergeObjectUser, addedUserNames);
        }
      //  printAllUserData();
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
        for (Map.Entry<UserType, MergeObject> entry : sourceMap.entrySet()) {
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


