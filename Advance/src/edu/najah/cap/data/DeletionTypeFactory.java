package edu.najah.cap.data;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class DeletionTypeFactory {
    public DeletionType getType(String deletionType, IPayment paymentService, 
    IUserActivityService userActivityService, 
    IPostService postService, IUserService userService) {
        if ("hard".equals(deletionType)) {
            return new HardDeletion(paymentService, userActivityService, postService, userService);
        } else if ("soft".equals(deletionType)) {
            return new SoftDeletion(paymentService, userActivityService, postService, userService);
        }
        return null;
    }
}
