package edu.najah.cap.data;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegularUserStrategy implements UserTypeStrategy {
    private static final Logger LOGGER = Logger.getLogger(RegularUserStrategy.class.getName());
    private IUserActivityService userActivityService;

    public RegularUserStrategy(IUserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @Override
    public void collectUserData(MergeObject mergeObject, String userName) {
        try {
            List<UserActivity> userActivities = userActivityService.getUserActivity(userName);
            mergeObject.setUserActivities(userActivities);
        } catch (NotFoundException e) {
            LOGGER.log(Level.WARNING, "User not found: " + userName, e);
            // Handle specific actions for NotFoundException
        } catch (SystemBusyException e) {
            LOGGER.log(Level.WARNING, "System is busy, cannot process user data for: " + userName, e);
            // Handle specific actions for SystemBusyException
        } catch (BadRequestException e) {
            LOGGER.log(Level.SEVERE, "Bad request for user: " + userName, e);
            // Handle specific actions for BadRequestException
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occurred while collecting user data for: " + userName, e);
            // Handle unexpected exceptions
        }
    }
}
