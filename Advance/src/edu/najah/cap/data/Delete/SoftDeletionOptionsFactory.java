package edu.najah.cap.data.Delete;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;


import edu.najah.cap.data.LoggerSetup;

import edu.najah.cap.iam.UserType;
public class SoftDeletionOptionsFactory {
    private Scanner scanner;
    private static final Logger logger = LoggerSetup.getLogger(); 

    public SoftDeletionOptionsFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public Deletion getSoftDeletionAction(UserType userType, DeletionActionFactory actionFactory) {
        logger.info("Displaying soft deletion options for user type: " + userType);
        
        System.out.println("Please choose the type of data you want to delete:");
        if (userType == UserType.NEW_USER || userType == UserType.REGULAR_USER || userType == UserType.PREMIUM_USER) {
            System.out.println("1. Delete Posts Data");
        }
        if (userType == UserType.REGULAR_USER || userType == UserType.PREMIUM_USER) {
            System.out.println("2. Delete Activities Data");
        }
        if (userType == UserType.PREMIUM_USER) {
            System.out.println("3. Delete Transactions Data");
        }

        System.out.print("Enter your choice: ");
        int dataTypeChoice = scanner.nextInt();

        Deletion deletionAction = actionFactory.getAction(dataTypeChoice);
        if (deletionAction == null) {
            logger.warning("Invalid deletion type selected: " + dataTypeChoice);
            System.out.println("Invalid option selected.");
        } else {
            logger.info("Soft deletion action created for type: " + dataTypeChoice);
        }
        
        return deletionAction;
    }
}
