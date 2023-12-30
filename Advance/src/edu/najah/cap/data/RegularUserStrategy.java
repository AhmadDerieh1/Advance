package edu.najah.cap.data;

import java.util.List;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public class RegularUserStrategy implements UserTypeStrategy {
    private IUserActivityService userActivityService;

    public RegularUserStrategy(IUserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @Override
    public void collectUserData(MergeObject mergeObject, String userName) {
        try{
        List<UserActivity> userActivities = userActivityService.getUserActivity(userName);
        mergeObject.setUserActivities(userActivities);
        }catch (NotFoundException | SystemBusyException | BadRequestException e) {
     
        }
    }
}