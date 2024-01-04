package edu.najah.cap.data.Delete;

import java.util.logging.Logger;
import edu.najah.cap.data.FakeDataBase;
import edu.najah.cap.data.LoggerSetup;

public class HardDeletion implements DeletionType {
    private static final Logger logger = LoggerSetup.getLogger(); 

    @Override
    public void executeDeletion(String userName) {
        try {
            FakeDataBase.deleteUser(userName);
            logger.info("Hard deletion completed for user: " + userName);
        } catch (Exception e) {
            logger.severe("Error during hard deletion for user: " + userName);
            logger.severe(e.getMessage());
        }
    }
}

