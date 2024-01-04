package edu.najah.cap.data.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.export.ExportPaymentsToPdf;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public class DeleteActivities implements Deletion {
 private static final Logger logger = LoggerSetup.getLogger(); 
    private IUserActivityService userActivityService;

    public DeleteActivities(IUserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }
    @Override
    public void removeData(String userName) {
        try {
            List<UserActivity> activities = userActivityService.getUserActivity(userName);
            for (UserActivity activity : new ArrayList<>(activities)) {
                userActivityService.removeUserActivity(userName, activity.getId());
            }
            logger.info("Successfully removed activities for user: " + userName);
        } catch (SystemBusyException | BadRequestException | NotFoundException e) {
            // Handle specific exceptions
            logger.warning("Exception occurred while removing activities for user: " + userName);
            logger.warning(e.getMessage());
        } catch (Exception e) {
            // Handle other unforeseen exceptions
            logger.severe("Unexpected exception occurred while removing activities for user: " + userName);
            logger.severe(e.getMessage());
        }
    }
}
