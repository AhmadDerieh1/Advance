package edu.najah.cap.data;


import java.util.List;

import edu.najah.cap.activity.UserActivity;

import edu.najah.cap.iam.UserProfile;



import edu.najah.cap.payment.Transaction;
import edu.najah.cap.posts.Post;

public class MergeObject {
    private UserProfile userProfile;
    private List<Post> posts;
    private List<Transaction> transactions;
    private List<UserActivity> userActivities;
 
    public List<UserActivity> getUserActivities() {
        return userActivities;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    public List<Post> getPosts() {
       return posts;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setUserActivities(List<UserActivity> userActivities) {
        this.userActivities = userActivities;
    }
    public UserProfile getUserProfile() {
        return userProfile;
    }

   
    public void printData() {
     
    System.out.println("UserProfile: ");
    System.out.println("  FirstName: " + userProfile.getFirstName());
    System.out.println("  LastName: " + userProfile.getLastName());
    System.out.println("  PhoneNumber: " + userProfile.getPhoneNumber());
    System.out.println("  Email: " + userProfile.getEmail());
    System.out.println("  UserName: " + userProfile.getUserName());
    System.out.println("  Password: " + userProfile.getPassword());
    System.out.println("  Role: " + userProfile.getRole());
    System.out.println("  Department: " + userProfile.getDepartment());
    System.out.println("  Organization: " + userProfile.getOrganization());
    System.out.println("  Country: " + userProfile.getCountry());
    System.out.println("  City: " + userProfile.getCity());
    System.out.println("  Street: " + userProfile.getStreet());
    System.out.println("  PostalCode: " + userProfile.getPostalCode());
    System.out.println("  Building: " + userProfile.getBuilding());
    System.out.println("  UserType: " + userProfile.getUserType());
    
    System.out.println("--------------------------");

  
    System.out.println("\nPosts:");
    if (posts != null) {
        for (Post post : posts) {
            System.out.println("  Post ID: " + post.getId());
            System.out.println("  Title: " + post.getTitle());
        
        }
    }

  
    System.out.println("\nTransactions:");
    if (transactions != null) {
        for (Transaction transaction : transactions) {
            System.out.println("  Transaction ID: " + transaction.getId());
            System.out.println("  Amount: " + transaction.getAmount());
        }
    }


    System.out.println("\nUser Activities:");
    if (userActivities != null) {
        for (UserActivity userActivity : userActivities) {
            System.out.println("  Activity ID: " + userActivity.getId());
            System.out.println("  Activity Type: " + userActivity.getActivityType());
        }
    }
}

    
 

    
}