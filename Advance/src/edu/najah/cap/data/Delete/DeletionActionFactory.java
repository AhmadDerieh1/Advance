package edu.najah.cap.data.Delete;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class DeletionActionFactory {
    private IUserActivityService userActivityService;
    private IPayment paymentService;
    private IPostService postService;
    private IUserService userService;


    public DeletionActionFactory(IUserActivityService userActivityService, 
    IPayment paymentService,IPostService postService,IUserService userService) {
        this.userActivityService = userActivityService;
        this.paymentService = paymentService;
        this.postService = postService;
         this.userService = userService;
    }

    public Deletion getAction(String actionType) {
        switch (actionType) {
            case "payment":
                return new DeleteTransactions(paymentService);
            case "activity":
                return new DeleteActivities(userActivityService);
            case "post":
                return new DeletePosts(postService);
            case "userprofile":
                return new DeleteUserData(userService);
            default:
                return null;
        }
    }
}
