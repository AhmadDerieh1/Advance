package edu.najah.cap.data.Delete;

import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class SoftDeletion implements DeletionType {
 private static final Logger logger = LoggerSetup.getLogger(); 
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
        try {
            // Perform soft deletion actions by calling the factory method
            DeletionActionFactory factory = new DeletionActionFactory(userActivityService, paymentService, postService, userService);
            Deletion deletionAction = factory.getAction("soft"); 
            if (deletionAction != null) {
                deletionAction.removeData(userName);
                logger.info("Soft deletion completed for user: " + userName);
            } else {
                logger.warning("Unsupported soft deletion action type.");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur
            logger.warning("Exception occurred during soft deletion for user: " + userName);
            logger.warning(e.getMessage());
        }
    }
}
