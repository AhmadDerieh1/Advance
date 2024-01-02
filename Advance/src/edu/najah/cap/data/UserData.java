package edu.najah.cap.data;

//Singleton
public class UserData implements Database {
    
    // connection is open for each user
     private static UserData instance = new UserData();
    //Overriding of Database class
    public void connect() {
        System.out.println("Connecting to FakeDatabase!!");
    }
    //Overriding of Database class
    public  void disconnect() {
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
  
     public MergeObject getMergeObjectForUser(String userName, FakeDataBase DB) {
     return FakeDataBase.UserInDatabase(userName);
}
}
     

