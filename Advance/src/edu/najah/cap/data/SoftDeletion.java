package edu.najah.cap.data;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class SoftDeletion implements DeletionType {
     private IPayment paymentService;
    private IUserActivityService userActivityService;
    private IPostService postService;
    private IUserService userService;
    public SoftDeletion(IPayment paymentService, IUserActivityService userActivityService, 
    IPostService postService, IUserService userService) {
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
        this.postService = postService;
        this.userService = userService;
    }
    
    @Override
    public void executeDeletion(String userName) {
    DeletionActionFactory factory = new DeletionActionFactory(userActivityService, paymentService, 
    postService,userService);
   // factory.getAction(actionType);

    }
}