package edu.najah.cap.data.Delete;
import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

import java.util.HashSet;
import java.util.Set;

public class HardDeletion implements DeletionType{
    private IPayment paymentService;
    private IUserActivityService userActivityService;
    private IPostService postService;
    private IUserService userService;

    public HardDeletion(IPayment paymentService, IUserActivityService userActivityService, 
    IPostService postService, IUserService userService) {
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
        this.postService = postService;
        this.userService = userService;
    }
    @Override
public void executeDeletion(String userName) {
    try {
        paymentService.getTransactions(userName);
        userActivityService.getUserActivity(userName);
        postService.getPosts(userName);
        userService.getUser(userName);
    } catch (SystemBusyException | BadRequestException | NotFoundException e) {
        e.printStackTrace();
      
    }


}
 
}