package edu.najah.cap.data;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.data.export.DataExporter;
import edu.najah.cap.data.export.ExportFactory;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.exceptions.Util;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.payment.Transaction;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;
import edu.najah.cap.posts.PostService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;



public class Application {

    private static final IUserActivityService userActivityService = new UserActivityService();
    private static final IPayment paymentService = new PaymentService();
    private static final IUserService userService = new UserService();
    private static final IPostService postService = new PostService();

    private static String loginUserName;


    public static void main(String[] args) throws IOException, GeneralSecurityException {
        generateRandomData();
        Instant start = Instant.now();
        System.out.println("Application Started: " + start);
        //TODO Your application starts here. Do not Change the existing code

    DataFacade dataFacade = new DataFacadeImpl(userService, postService, paymentService, userActivityService);
    FakeDataBase DB =new FakeDataBase(dataFacade);
    
    try {
        DB.initializeFakeData();
        Scanner scanner = new Scanner(System.in);
        System.out.println("! Welcome to our system !");
        System.out.print("Please Enter your username ");
        System.out.println("'Note: You can use any of the following usernames: user0, user1, user2, user3, .... user99'");
        //FakeDataBase.DB;
        String userName = scanner.nextLine();
        setLoginUserName(userName);

       UserData userDataSingleton = UserData.getInstance();

       //objData_Current user

       //userMergeObject = contains all the user data in the system according to his type
        MergeObject userMergeObject = userDataSingleton.getMergeObjectForUser(getLoginUserName(),DB);
           if (userMergeObject != null && userMergeObject.getUserProfile() != null) {
            if (DB.isUserInDatabase(userMergeObject.getUserProfile().getUserName())) {
                System.out.println("Username " + userMergeObject.getUserProfile().getUserName() + " is found in the database.");
        
            } else {
                System.out.println("Username " + getLoginUserName() + " is not found in the database.");

            }
        } else {
        
            System.out.println("User data could not be retrieved for username: " + getLoginUserName());
        
        }
            
 if (userMergeObject != null) {
System.out.println("________________________");
System.out.println("User Profile Name: " + userMergeObject.getUserProfile().getUserName());
System.out.println("User LastName: " + userMergeObject.getUserProfile().getLastName());
System.out.println("User Email: " + userMergeObject.getUserProfile().getEmail());
System.out.println("User Password: " + userMergeObject.getUserProfile().getPassword());
System.out.println("________________________");
  } else {
        System.out.println("User not found.");
    }
   try {
            ExportFactory exportFactory = new ExportFactory();
            DataExporter exporter = exportFactory.createExport("PDF");
            exporter.exportData(userMergeObject);
            DataExporter zip = exportFactory.createExport("ZIP");
            zip.exportData(userMergeObject);
        }catch (Exception e )
        {
            System.out.println(e.getMessage());
        }

       // DB.printAllUserData();
    } catch (Exception e) {

    }


        //TODO Your application ends here. Do not Change the existing code
        Instant end = Instant.now();
        System.out.println("Application Ended: " + end);
    }
   
    private static void generateRandomData() {
        Util.setSkipValidation(true);
        for (int i = 0; i < 100; i++) {
            generateUser(i);
            generatePost(i);
            generatePayment(i);
            generateActivity(i);
        }
        System.out.println("Data Generation Completed");
        Util.setSkipValidation(false);
    }


    private static void generateActivity(int i) {
        for (int j = 0; j < 100; j++) {
            try {
                if(UserType.NEW_USER.equals(userService.getUser("user" + i).getUserType())) {
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Error while generating activity for user" + i);
            }
            userActivityService.addUserActivity(new UserActivity("user" + i, "activity" + i + "." + j, Instant.now().toString()));
        }
    }

    private static void generatePayment(int i) {
        for (int j = 0; j < 100; j++) {
            try {
                if (userService.getUser("user" + i).getUserType() == UserType.PREMIUM_USER) {
                    paymentService.pay(new Transaction("user" + i, i * j, "description" + i + "." + j));
                }
            } catch (Exception e) {
                System.err.println("Error while generating post for user" + i);
            }
        }
    }

    private static void generatePost(int i) {
        for (int j = 0; j < 100; j++) {
            postService.addPost(new Post("title" + i + "." + j, "body" + i + "." + j, "user" + i, Instant.now().toString()));
        }
    }

    private static void generateUser(int i) {
        UserProfile user = new UserProfile();
        user.setUserName("user" + i);
        user.setFirstName("first" + i);
        user.setLastName("last" + i);
        user.setPhoneNumber("phone" + i);
        user.setEmail("email" + i);
        user.setPassword("pass" + i);
        user.setRole("role" + i);
        user.setDepartment("department" + i);
        user.setOrganization("organization" + i);
        user.setCountry("country" + i);
        user.setCity("city" + i);
        user.setStreet("street" + i);
        user.setPostalCode("postal" + i);
        user.setBuilding("building" + i);
        user.setUserType(getRandomUserType(i));
        userService.addUser(user);
    }

    private static UserType getRandomUserType(int i) {
        if (i > 0 && i < 3) {
            return UserType.NEW_USER;
        } else if (i > 3 && i < 7) {
            return UserType.REGULAR_USER;
        } else {
            return UserType.PREMIUM_USER;
        }
    }

    public static String getLoginUserName() {
        return loginUserName;
    }

    private static void setLoginUserName(String loginUserName) {
        Application.loginUserName = loginUserName;
    }
}

