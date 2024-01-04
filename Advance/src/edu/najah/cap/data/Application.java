package edu.najah.cap.data;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.data.Delete.Deletion;
import edu.najah.cap.data.Delete.DeletionActionFactory;
import edu.najah.cap.data.Delete.DeletionType;
import edu.najah.cap.data.Delete.DeletionTypeFactory;
import edu.najah.cap.data.Delete.HardDeletion;
import edu.najah.cap.data.Delete.SoftDeletion;
import edu.najah.cap.data.export.DataExporter;
import edu.najah.cap.data.export.ExportFactory;
import edu.najah.cap.data.export.GoogleDriveService;
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
import java.util.logging.Level;
import java.util.logging.Logger;



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
    DB.connect();
    try {
        DB.initializeFakeData();
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("! Welcome to our system !");
        System.out.print("Please Enter your username ");
        System.out.println("'Note: You can use any of the following usernames: user0, user1, user2, user3, .... user99'");
        String userName = scanner.nextLine();
        setLoginUserName(userName);

       UserData userDataSingleton = UserData.getInstance();

        MergeObject userMergeObject = userDataSingleton.getMergeObjectForUser(getLoginUserName(),DB);
           if (userMergeObject != null && userMergeObject.getUserProfile() != null) {
            if (DB.isUserInDatabase(userMergeObject.getUserProfile().getUserName())) {
                System.out.println("Welcome to our " +userMergeObject.getUserProfile().getUserType()+" "+userMergeObject.getUserProfile().getUserName() + " !");
            } else {
                // is not found in the database.
                System.out.println("Oops"  + getLoginUserName() + ", It seems that you are not registered in our system ");
            }
        } else {
            System.out.println("User data could not be retrieved for username: " + getLoginUserName());
        }
        ExportFactory exportFactory = new ExportFactory();
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1 See the names of all users registered in our system");
            System.out.println("2 Want to see your information in our system ");
            //Decoreter Pattren
            System.out.println("3 Want to see all your information in our system in a pdf file ");
            System.out.println("4 Aggregate your information into a zip file and download it directly to your device ");
            System.out.println("5 Aggregate your information into a zip file and  uploading the compressed file to your Google Drive ");
            //deletion
            System.out.println("6 Deletion");
            System.out.println("7 Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            try {
            switch (choice) {
                case 1:
                    DB.printAllUserData();
                    break;
                case 2:
 if (userMergeObject != null) {
System.out.println("Choose the data number you want to display:");
System.out.println("1. Your profile data");
System.out.println("2. Your post data");
System.out.println("3. Your activity data");
System.out.println("4. Your payment data");
PrinterFactory factory = new ConcretePrinterFactory();
Printer data;
int choiceData = scanner.nextInt();
try {
    data = factory.createPrinter(choiceData);
    data.print(userMergeObject);
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
System.out.println("________________________");
  } else {
        System.out.println("User not found.");
    }

    case 3:
    
    try {
        DataExporter pdfExporter = exportFactory.createExport("PDF");
        pdfExporter.exportData(userMergeObject);
    } catch (Exception e) {
        System.out.println("Error during PDF export: " + e.getMessage());    }
    break;
    case 4:
    try {
        DataExporter zipExporter = exportFactory.createExport("ZIP");
        String zipFileName = zipExporter.exportData(userMergeObject);
        System.out.println("ZIP file created: " + zipFileName);
    } catch (Exception e) {
        System.out.println("An error occurred during ZIP export: " + e.getMessage());
    }
    break;

    case 5: 
                try {
                  
                    DataExporter googleDriveExporter = exportFactory.createExport("GoogleDrive");
                    String driveFileId = googleDriveExporter.exportData(userMergeObject);
                    System.out.println("Google Drive File ID: " + driveFileId);
                } catch (Exception e) {
                 
                    System.out.println("An error occurred during Google Drive upload: " + e.getMessage());
                }
                break;

                    case 6:
                   
                    System.out.println("Please choose the type of deletion:");
                    System.out.println("1. Hard Delete (Complete removal of your account)");
                    System.out.println("2. Soft Delete (You want to delete a specific type of your data)");
                    System.out.print("Enter your choice: ");
                    int deletionChoice = scanner.nextInt();
                    DeletionTypeFactory deletionTypeFactory = new DeletionTypeFactory();
                    DeletionType deletionType = null;
                    if (deletionChoice == 1) {
                        

System.out.println("You have chosen to completely delete your account. This action cannot be undone and you will not be able to create a new account with the same username in the future. Are you sure? (yes/no)");
                    String confirmation = scanner.next();
                    if ("yes".equalsIgnoreCase(confirmation)) {
                         //to registering the names of users who have completely deleted
                        UserData userData = UserData.getInstance();
                        userData.deleteUser(getLoginUserName());
                        //delete opration
                        DeletionType hardDelete = new HardDeletion(); 
                        hardDelete.executeDeletion(getLoginUserName());
                       
                       
                    }
                        deletionType = deletionTypeFactory.getType("hard", paymentService, userActivityService, postService, userService);
                        DB.printAllUserData();
                         return;
                    } else if (deletionChoice == 2) {
                         
                   deletionType = deletionTypeFactory.getType("soft", paymentService, userActivityService, postService, userService);
     if (deletionType != null) {
    System.out.println("Please choose the type of data you want to delete:");
    System.out.println("1. Delete Posts Data");
     System.out.println("2. Delete Activities Data");
    System.out.println("3. Delete Transactions Data");
    System.out.print("Enter your choice: ");
    int dataTypeChoice = scanner.nextInt();
DeletionActionFactory factory = new DeletionActionFactory(userActivityService, paymentService, postService);
Deletion deletionAction = factory.getAction(dataTypeChoice); 
deletionAction.removeData(getLoginUserName());
     }
break;}
                
                    case 7:
                        System.out.println("Exiting...");
                     
                        return;
                    
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
          
                }
}
  
            
        } catch (Exception e) {
            System.out.println("An error occurred during database initialization: " + e.getMessage());
            e.printStackTrace();
     
        } finally {
            DB.disconnect();
        
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

