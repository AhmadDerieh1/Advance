package edu.najah.cap.export;

import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.posts.PostService;

public class ExportFactory {
    private UserService userService;
    private PostService postService;
    private PaymentService paymentService;
    private UserActivityService userActivityService;
    private UserType userType; // New field to store UserType

    // Updated constructor with UserType parameter
    public ExportFactory(UserService userService, PostService postService, PaymentService paymentService, UserActivityService userActivityService,UserType userType) {
        this.userService = userService;
        this.postService = postService;
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
        this.userType = userType;
    }

    public DataExporter createExport(String type) {
        if (type.equals("PDF")) {
            // You can now use userType here if needed
            return new DirectExporter(userService, postService, paymentService, userActivityService, userType);
        }

        throw new IllegalArgumentException("Unsupported export type: " + type);
    }
}
