package edu.najah.cap.data.Delete;

import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.data.LoggerSetup;

import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class SoftDeletion implements DeletionType {
 private static final Logger logger = LoggerSetup.getLogger(); 
    private IPayment paymentService;
    private IUserActivityService userActivityService;
    private IPostService postService;
   
// new SoftDeletion(paymentService, userActivityService, postService)
    public SoftDeletion(IPayment paymentService, IUserActivityService userActivityService,
                        IPostService postService) {
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
        this.postService = postService;

    }

    @Override
    public void executeDeletion(String userName) {
        try {
       
           
        } catch (Exception e) {
            // Handle any exceptions that may occur
            logger.warning("Exception occurred during soft deletion for user: " + userName);
            logger.warning(e.getMessage());
        }
    }
}
