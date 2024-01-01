package edu.najah.cap.data;

import java.util.HashSet;
import java.util.Set;

//Singleton
public class UserData implements Database {
    // connection is open for each user
     private static UserData instance = new UserData();
     //for delete
     private Set<String> deletedUserNames = new HashSet<>();

    //constracter
    private UserData(){}
    //Overriding of Database class
    public void start() {
        System.out.println("Connecting to FakeDatabase!!");
    }
    //Overriding of Database class
    public  void end() {
        System.out.println("Disconnected from the FakeDatabase.");
    }

    // implementation users the Singleton to ensure a single connection for each user
    public static UserData getInstance() {
        //to avoid problems if happen Multithreading
        if (instance == null) {
            synchronized (UserData.class) {

                if (instance == null) {
                    instance = new UserData();
                }
                
            }
        }
        return instance;
    }
    //to give me objData_Current user
     public MergeObject getMergeObjectForUser(String userName, FakeDataBase DB) {
     return FakeDataBase.UserInDatabase(userName);
}

    public void deleteUser(String userName) {
        FakeDataBase.deleteUser(userName);
        deletedUserNames.add(userName);
    }
//print(make new account?)
//print(enter youre name)
//input( )
    public boolean canCreateUser(String userName) {
        return !deletedUserNames.contains(userName);
    }

}
     
