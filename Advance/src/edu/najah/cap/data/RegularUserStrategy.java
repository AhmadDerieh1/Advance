package edu.najah.cap.data;
import java.util.logging.Logger;
import java.util.List;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public class RegularUserStrategy implements UserTypeStrategy {
    private IUserActivityService userActivityService;
    private static final Logger logger = Logger.getLogger(RegularUserStrategy.class.getName());

    public RegularUserStrategy(IUserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @Override
    public void collectUserData(MergeObject mergeObject, String userName) {
        try {
            // Log the start of data collection
            logger.info("Collecting data for Regular User: " + userName);

            // Collect and set User Activities
            List<UserActivity> userActivities = userActivityService.getUserActivity(userName);
            mergeObject.setUserActivities(userActivities);

            // Log the completion of data collection
            logger.info("Data collection completed for Regular User: " + userName);
        } catch (NotFoundException | SystemBusyException | BadRequestException e) {
            // Handle exceptions and log them
            logger.warning("Exception occurred while collecting data for Regular User: " + userName);
            logger.warning(e.getMessage());
        }
    }
}