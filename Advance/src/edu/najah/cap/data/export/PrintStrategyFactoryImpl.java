package edu.najah.cap.data.export;

import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.UserData;
import edu.najah.cap.iam.UserType;

public class PrintStrategyFactoryImpl implements PrintStrategyFactory {

     private static final Logger logger = LoggerSetup.getLogger(); 



    @Override
    public PrintStrategyCreator createPrintStrategy(UserType userType) {
        try {
            logger.log(Level.INFO, "Creating print strategy for user type: " + userType);

            if (userType.equals(UserType.NEW_USER)) {
                return new NawPrintStrategyCreator();
            } else if (userType.equals(UserType.REGULAR_USER)) {
                return new RegularPrintStrategyCreator();
            } else if (userType.equals(UserType.PREMIUM_USER)) {
                return new PremiumPrintStrategyCreator();
            } else {
                throw new IllegalArgumentException("Invalid user type: " + userType);
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error in creating print strategy: " + e.getMessage(), e);
            throw e;  // Rethrow the exception after logging it
        }
    }
}
