package edu.najah.cap.data.Delete;

import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class DeletionTypeFactory {
    private static final Logger logger = Logger.getLogger(DeletionTypeFactory.class.getName());

    public DeletionType getType(String deletionType, IPayment paymentService,
                                IUserActivityService userActivityService,
                                IPostService postService, IUserService userService) {
        try {
            if ("hard".equals(deletionType)) {
                return new HardDeletion(paymentService, userActivityService, postService, userService);
            } else if ("soft".equals(deletionType)) {
                return new SoftDeletion(paymentService, userActivityService, postService, userService);
            }
            return null;
        } catch (Exception e) {
            // Handle any exceptions that may occur while creating the DeletionType object
            logger.warning("Exception occurred while creating DeletionType object for deletion type: " + deletionType);
            logger.warning(e.getMessage());
            return null;
        }
    }
}
