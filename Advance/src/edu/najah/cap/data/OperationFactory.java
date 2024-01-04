package edu.najah.cap.data;

import java.util.Scanner;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.data.Delete.Deletion;
import edu.najah.cap.data.Delete.DeletionActionFactory;
import edu.najah.cap.data.Delete.DeletionType;
import edu.najah.cap.data.Delete.DeletionTypeFactory;
import edu.najah.cap.data.Delete.HardDeletion;
import edu.najah.cap.data.Delete.SoftDeletionOptionsFactory;
import edu.najah.cap.data.export.DataExporter;
import edu.najah.cap.data.export.ExportFactory;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;
import java.util.logging.Logger;


public class OperationFactory {
    private static final Logger logger = LoggerSetup.getLogger();
    private IPayment paymentService;
    private IUserActivityService userActivityService;
    private IPostService postService;
    private IUserService userService;

    public OperationFactory(IPayment paymentService, IUserActivityService userActivityService, IPostService postService, IUserService userService) {
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
        this.postService = postService;
        this.userService = userService;
    }

    public void executeOperation(Scanner scanner, MergeObject userMergeObject,FakeDataBase DB) {
        ExportFactory exportFactory = new ExportFactory();
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1 See the names of all users registered in our system");
            System.out.println("2 Want to see your information in our system ");
            // Decorator Pattern
            System.out.println("3 Want to see all your information in our system in a PDF file ");
            System.out.println("4 Aggregate your information into a zip file and download it directly to your device ");
            System.out.println("5 Aggregate your information into a zip file and upload the compressed file to your Google Drive ");
            // Deletion
            System.out.println("6 Deletion");
            System.out.println("7 Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
                
                try {
                switch (choice) {
                    case 1:
                    logger.info("1 See the names of all users registered in our system");
                    DB.printAllUserData();
                        break;

                    case 2:
                logger.info("2 Want to see your information in our system ");
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
                break;
                case 3:  
                logger.info("3 Want to see all your information in our system in a PDF file ");            
                
                try {
                    DataExporter pdfExporter = exportFactory.createExport("PDF");
                    pdfExporter.exportData(userMergeObject);
                } catch (Exception e) {
                    System.out.println("Error during PDF export: " + e.getMessage());    }
                break;
                case 4:
                logger.info("4 Aggregate your information into a zip file and download it directly to your device ");
                try {
                    DataExporter zipExporter = exportFactory.createExport("ZIP");
                    String zipFileName = zipExporter.exportData(userMergeObject);
                    System.out.println("ZIP file created: " + zipFileName);
                } catch (Exception e) {
                    System.out.println("An error occurred during ZIP export: " + e.getMessage());
                }
                break;

                case 5: 
                logger.info("5 Aggregate your information into a zip file and upload the compressed file to your Google Drive ");
                    try {
                    
                        DataExporter googleDriveExporter = exportFactory.createExport("GoogleDrive");
                        String driveFileId = googleDriveExporter.exportData(userMergeObject);
                        System.out.println("Google Drive File ID: " + driveFileId);
                    } catch (Exception e) {
                    
                        System.out.println("An error occurred during Google Drive upload: " + e.getMessage());
                    }
                    break;

                        case 6:
                                          logger.info("6 Deletion");
                    
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
                            userData.deleteUser(userMergeObject.getUserProfile().getUserName());
                            //delete opration
                            DeletionType hardDelete = new HardDeletion(); 
                            hardDelete.executeDeletion(userMergeObject.getUserProfile().getUserName());
                        
                        
                        }
                            deletionType = deletionTypeFactory.getType("hard", paymentService, userActivityService, postService, userService);
                            DB.printAllUserData();
                            return;
    } else if (deletionChoice == 2) {
        UserType userType = userMergeObject.getUserProfile().getUserType();
        SoftDeletionOptionsFactory softDeletionOptionsFactory = new SoftDeletionOptionsFactory(scanner);
        DeletionActionFactory actionFactory = new DeletionActionFactory(userActivityService, paymentService, postService);
        Deletion deletionAction = softDeletionOptionsFactory.getSoftDeletionAction(userType, actionFactory);

        if (deletionAction != null) {
            deletionAction.removeData(userMergeObject.getUserProfile().getUserName(),userMergeObject);
            logger.info("Soft deletion action performed for user: " + userMergeObject.getUserProfile().getUserName());
             return;
        } else {
            System.out.println("No action performed. Invalid selection or cancellation.");
        }
    }
    break;
                        case 7:
                        logger.info("Exiting the application");
                            System.out.println("Exiting...");
                        
                            return;
                        
                        default:
                     logger.warning("Invalid option selected: " + choice);
                            System.out.println("Invalid option. Please try again.");
                    }
                } catch (Exception e) {
                     logger.severe("An error occurred: " + e.getMessage());
                    System.out.println("An error occurred: " + e.getMessage());
            
                    }
    }
}
}
