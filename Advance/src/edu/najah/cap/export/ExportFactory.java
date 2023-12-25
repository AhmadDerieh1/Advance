package edu.najah.cap.export;

import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.posts.PostService;

public class ExportFactory {
    private UserService userService;
    private PostService postService;
    private PaymentService paymentService;
    private UserActivityService userActivityService;
    public ExportFactory(UserService userService, PostService postService, PaymentService paymentService, UserActivityService userActivityService) {
        this.userService = userService;
        this.postService = postService;
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
    }

    public DataExporter createExport(String type) {
        if (type.equals("PDF")) {
            return new DirectExporter(userService, postService, paymentService, userActivityService);
        }

        throw new IllegalArgumentException("Unsupported export type: " + type);
    }
}