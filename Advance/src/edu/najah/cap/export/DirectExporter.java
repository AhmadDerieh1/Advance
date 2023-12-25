package edu.najah.cap.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.payment.Transaction; // Ensure this import is correct
import edu.najah.cap.posts.PostService;
import edu.najah.cap.posts.Post;

import java.io.FileOutputStream;
import java.util.List;

public class DirectExporter implements DataExporter {
    private UserService userService;
    private PostService postService;
    private PaymentService paymentService;

    private UserActivityService userActivityService;
    private String userName;

    public DirectExporter(UserService userService, PostService postService, PaymentService paymentService, UserActivityService userActivityService) {
        this.userService = userService;
        this.postService = postService;
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
    }

    @Override
    public void exportData(String userName) {
        this.userName = userName;
        exportUserProfileToPdf(userName);
    }

    public void exportUserProfileToPdf(String username) {
        Document document = new Document();
        UserProfile userProfile = userService.getUser(username);
        List<Post> posts = postService.getPosts(username);
        List<Transaction> transactions = paymentService.getTransactions(username);
        List<UserActivity> userActivities = userActivityService.getUserActivity(username);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("UserName_" + username.replaceAll("\\s+", "_") + ".pdf"));
            document.open();

            document.add(new Paragraph("UserName: " + userProfile.getUserName()));
            document.add(new Paragraph("First Name:" + userProfile.getFirstName()));
            document.add(new Paragraph("Last Name:" + userProfile.getLastName()));
            document.add(new Paragraph("Phone number:" + userProfile.getPhoneNumber()));
            document.add(new Paragraph("Email: " + userProfile.getEmail()));
            // It's usually a bad practice to include passwords in exported data
            // document.add(new Paragraph("Password:" + userProfile.getPassword()));
            document.add(new Paragraph("Role:" + userProfile.getRole()));
            document.add(new Paragraph("Department:" + userProfile.getDepartment()));
            document.add(new Paragraph("Organization:" + userProfile.getOrganization()));
            document.add(new Paragraph("Country:" + userProfile.getCountry()));
            document.add(new Paragraph("City:" + userProfile.getCity()));
            document.add(new Paragraph("Street:" + userProfile.getStreet()));
            document.add(new Paragraph("Post code:" + userProfile.getPostalCode()));
            document.add(new Paragraph("Building:" + userProfile.getBuilding()));
            document.add(new Paragraph("User type:" + userProfile.getUserType()));

            // Add post
            for (Post post : posts) {
                document.add(new Paragraph("Post Title: " + post.getTitle()));
            }

            // Add payment
            for (Transaction transaction : transactions) {
                document.add(new Paragraph("Payment Processed: " + transaction.getDescription()));
            }
            // Add activity
            for (UserActivity activity : userActivities) {
                document.add(new Paragraph("Activity created: " + activity.getActivityType()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
