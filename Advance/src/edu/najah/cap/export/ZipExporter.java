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

import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.payment.Transaction;
import edu.najah.cap.posts.Post;
import edu.najah.cap.posts.PostService;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipExporter implements DataExporter {
    private Document document;

    public ZipExporter() {
        document = new Document();
    }

    @Override
    public void exportData(String userName) throws SystemBusyException, NotFoundException, BadRequestException {
        try {
            // Check if the user exists before exporting data
            if (!userExists(userName)) {
                throw new NotFoundException("User does not exist: " + userName);
            }

            // Generate PDF content for each type
            byte[] postsPdf = generatePostsPdf(userName);
            byte[] activitiesPdf = generateActivitiesPdf(userName);
            byte[] paymentsPdf = generatePaymentsPdf(userName);

            // Zip the files
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("exported_data.zip"))) {
                // Add Posts PDF to the ZIP
                addPdfToZip(zipOutputStream, "Posts_" + userName.replaceAll("\\s+", "_") + ".pdf", postsPdf);

                // Add Activities PDF to the ZIP
                addPdfToZip(zipOutputStream, "Activities_" + userName.replaceAll("\\s+", "_") + ".pdf", activitiesPdf);

                // Add Payments PDF to the ZIP
                addPdfToZip(zipOutputStream, "Payments_" + userName.replaceAll("\\s+", "_") + ".pdf", paymentsPdf);

            } catch (IOException e) {
                e.printStackTrace();  // Handle the exception appropriately (e.g., log it)
            }
        } catch (NotFoundException | SystemBusyException | BadRequestException e) {
            // Handle exceptions or log them
            e.printStackTrace();
        }
    }

    // Add this method to check if the user exists
    private boolean userExists(String userName) {
        // Implement logic to check if the user exists in your system
        // Return true if the user exists, false otherwise
        return true; // Replace with actual logic
    }

    private byte[] generatePostsPdf(String username) throws SystemBusyException, NotFoundException, BadRequestException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Export Posts Data
            exportPostsToPdf(document, username);

        } catch (Exception e) {
            e.printStackTrace();  // Use a logger instead of printStackTrace
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] generateActivitiesPdf(String username) throws SystemBusyException, NotFoundException, BadRequestException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Export Activities Data
            exportActivitiesToPdf(document, username);

        } catch (Exception e) {
            e.printStackTrace();  // Use a logger instead of printStackTrace
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] generatePaymentsPdf(String username) throws SystemBusyException, NotFoundException, BadRequestException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Export Payments Data
            exportPaymentsToPdf(document, username);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    private void exportPostsToPdf(Document document, String username) throws DocumentException, SystemBusyException, BadRequestException, NotFoundException {
        PostService postService = new PostService();
        List<Post> posts = postService.getPosts(username);

        if (posts.isEmpty()) {
            document.add(new Paragraph("No posts available for user: " + username));
        } else {
            for (Post post : posts) {
                document.add(new Paragraph("Post Title: " + post.getTitle()));
            }
        }
    }

    private void exportActivitiesToPdf(Document document, String username) throws DocumentException, SystemBusyException, BadRequestException, NotFoundException {
        UserActivityService userActivityService = new UserActivityService();
        List<UserActivity> userActivities = userActivityService.getUserActivity(username);

        if (userActivities.isEmpty()) {
            document.add(new Paragraph("No activities available for user: " + username));
        } else {
            for (UserActivity activity : userActivities) {
                document.add(new Paragraph("Activity created: " + activity.getActivityType()));
                // Add more activity details as needed
            }
        }
    }

    private void exportPaymentsToPdf(Document document, String username) throws DocumentException, SystemBusyException, BadRequestException, NotFoundException {
        PaymentService paymentService = new PaymentService();
        List<Transaction> transactions = paymentService.getTransactions(username);

        if (transactions.isEmpty()) {
            document.add(new Paragraph("No payments available for user: " + username));
        } else {
            for (Transaction transaction : transactions) {
                document.add(new Paragraph("Payment Processed: " + transaction.getDescription()));
            }
        }
    }

    private void addPdfToZip(ZipOutputStream zipOutputStream, String entryName, byte[] pdfBytes) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(pdfBytes);
        zipOutputStream.closeEntry();
    }
}
