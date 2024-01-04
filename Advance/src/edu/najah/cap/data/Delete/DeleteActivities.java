package edu.najah.cap.data.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
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
    public boolean removeData(String userName, MergeObject mergeObject) {
        boolean isRemovedSuccessfully = false;
        try {
            List<UserActivity> activities = userActivityService.getUserActivity(userName);
            for (UserActivity activity : new ArrayList<>(activities)) {
                userActivityService.removeUserActivity(userName, activity.getId());
                mergeObject.getUserActivities().remove(activity);

            }
            mergeObject.setUserActivities(new ArrayList<>()); 
            logger.info("Successfully removed activities for user: " + userName);
            isRemovedSuccessfully = true;
        } catch (SystemBusyException | BadRequestException | NotFoundException e) {
            logger.warning("Exception occurred while removing activities for user: " + userName);
            logger.warning(e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected exception occurred while removing activities for user: " + userName);
            logger.severe(e.getMessage());
        }
        return isRemovedSuccessfully;
    }
}


