package edu.najah.cap.data;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.payment.IPayment;

public class PremiumUserStrategy implements UserTypeStrategy {
    private IPayment paymentService;
    private IUserActivityService userActivityService;

    public PremiumUserStrategy(IPayment paymentService, IUserActivityService userActivityService) {
        //super
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
    }

    @Override
    public void collectUserData(MergeObject mergeObject, String userName) {
        try{
        mergeObject.setTransactions(paymentService.getTransactions(userName));
        mergeObject.setUserActivities(userActivityService.getUserActivity(userName));
        }catch (NotFoundException | SystemBusyException | BadRequestException e) {
     
        }
    }

}
