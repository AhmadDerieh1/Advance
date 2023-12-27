package edu.najah.cap.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.payment.Transaction;
import edu.najah.cap.posts.Post;
import edu.najah.cap.posts.PostService;

import java.io.FileOutputStream;
import java.util.List;

public class DirectExporter implements DataExporter {
    Document document;
    public  DirectExporter()
    {
        document = new Document();
    }

    @Override
    public void exportData(String userName) throws SystemBusyException, NotFoundException, BadRequestException {
        exportUserProfileToPdf(userName);
    }

    private void exportUserProfileToPdf(String username) throws SystemBusyException, NotFoundException, BadRequestException {

        UserService userService = new UserService();
        UserProfile userProfile = userService.getUser(username);

        try {
            PdfWriter.getInstance(document, new FileOutputStream("UserName_" + username.replaceAll("\\s+", "_") + ".pdf"));
            document.open();
            // Export User Profile Data

            UserType userType = userProfile.getUserType();
            exportUserProfileDataToPdf(document, username, userProfile);
            exportPostsToPdf(document,username);
             if (userType.equals(UserType.REGULAR_USER)){
                exportActivitiesToPdf(document,username);
            }else if (userType.equals(UserType.PREMIUM_USER)){
                exportActivitiesToPdf(document,username);
                exportPaymentsToPdf(document,username);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Use a logger instead of printStackTrace
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }

    private void exportUserProfileDataToPdf(Document document, String username, UserProfile userProfile) throws DocumentException {
        // Add User Profile Information
        document.add(new Paragraph("UserName: " + userProfile.getUserName()));
        document.add(new Paragraph("First Name:" + userProfile.getFirstName()));
        document.add(new Paragraph("Last Name:" + userProfile.getLastName()));
        document.add(new Paragraph("Phone number:" + userProfile.getPhoneNumber()));
        document.add(new Paragraph("Email: " + userProfile.getEmail()));
        document.add(new Paragraph("Role:" + userProfile.getRole()));
        document.add(new Paragraph("Department:" + userProfile.getDepartment()));
        document.add(new Paragraph("Organization:" + userProfile.getOrganization()));
        document.add(new Paragraph("Country:" + userProfile.getCountry()));
        document.add(new Paragraph("City:" + userProfile.getCity()));
        document.add(new Paragraph("Street:" + userProfile.getStreet()));
        document.add(new Paragraph("Post code:" + userProfile.getPostalCode()));
        document.add(new Paragraph("Building:" + userProfile.getBuilding()));
        document.add(new Paragraph("User type:" + userProfile.getUserType()));

    }


    private void exportPostsToPdf(Document document, String username) throws DocumentException, SystemBusyException, BadRequestException, NotFoundException {

        PostService postService = new PostService();
        List<Post> posts = postService.getPosts(username);
        for (Post post : posts) {
            document.add(new Paragraph("Post Title: " + post.getTitle()));
        }
    }
    private void exportActivitiesToPdf(Document document, String username) throws DocumentException, SystemBusyException, BadRequestException, NotFoundException {
        UserActivityService userActivityService = new UserActivityService();
        List<UserActivity> userActivities = userActivityService.getUserActivity(username);
        System.out.println("User Activity");
        for (UserActivity activity : userActivities) {
            document.add(new Paragraph("Activity created: " + activity.getActivityType()));
            // Add more activity details as needed
        }
    }

    private void exportPaymentsToPdf(Document document, String username) throws DocumentException, SystemBusyException, BadRequestException, NotFoundException {
        PaymentService paymentService = new PaymentService();
        List<Transaction> transactions = paymentService.getTransactions(username);
        for (Transaction transaction : transactions) {
            document.add(new Paragraph("Payment Processed: " + transaction.getDescription()));
        }
    }
}

