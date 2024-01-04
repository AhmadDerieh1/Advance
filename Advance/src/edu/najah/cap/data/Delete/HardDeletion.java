package edu.najah.cap.data.Delete;

import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;


public class HardDeletion implements DeletionType {
 private static final Logger logger = LoggerSetup.getLogger(); 
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
            // Handle exceptions for payment service
            paymentService.getTransactions(userName);

            // Handle exceptions for user activity service
            userActivityService.getUserActivity(userName);

            // Handle exceptions for post service
            postService.getPosts(userName);

            // Handle exceptions for user service
            userService.getUser(userName);

            // Log successful execution
            logger.info("Hard deletion completed for user: " + userName);
        } catch (SystemBusyException | BadRequestException | NotFoundException e) {
            // Handle specific exceptions
            logger.warning("Exception occurred during hard deletion for user: " + userName);
            logger.warning(e.getMessage());
        } catch (Exception e) {
            // Handle other unforeseen exceptions
            logger.severe("Unexpected exception occurred during hard deletion for user: " + userName);
            logger.severe(e.getMessage());
        }
    }
}
