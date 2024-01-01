package edu.najah.cap.data;

import java.util.ArrayList;
import java.util.List;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public class DeleteActivities implements Deletion {
    private IUserActivityService userActivityService;
    public DeleteActivities( IUserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @Override
    public void removeData(String userName) {
       try {
            List<UserActivity> activities = userActivityService.getUserActivity(userName);
            for (UserActivity activity : new ArrayList<>(activities)) {
                userActivityService.removeUserActivity(userName, activity.getId());
            }
        } catch (SystemBusyException | BadRequestException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}