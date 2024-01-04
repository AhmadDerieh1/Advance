package edu.najah.cap.data.Delete;

import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class DeletionActionFactory {
 private static final Logger logger = LoggerSetup.getLogger(); 
    private IUserActivityService userActivityService;
    private IPayment paymentService;
    private IPostService postService;


    public DeletionActionFactory(IUserActivityService userActivityService,
                                 IPayment paymentService, IPostService postService) {
        this.userActivityService = userActivityService;
        this.paymentService = paymentService;
        this.postService = postService;

    }

    public Deletion getAction(int actionType) {
        try {
            switch (actionType) {
                case 1:
                    return new DeleteTransactions(paymentService);
                case 2:
                    return new DeleteActivities(userActivityService);
                case 3:
                    return new DeletePosts(postService);
                default:
                    return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur while creating the Deletion object
            logger.warning("Exception occurred while creating Deletion object for action type: " + actionType);
            logger.warning(e.getMessage());
            return null;
        }
    }
}
