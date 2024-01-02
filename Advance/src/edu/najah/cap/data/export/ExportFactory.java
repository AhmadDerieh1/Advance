package edu.najah.cap.data.export;

import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.posts.PostService;

public class ExportFactory {

    // Updated constructor with UserType parameter

    public DataExporter createExport(String type) {
        if (type.equals("PDF")) {
            // You can now use userType here if needed
           return new DirectExporter();
        }else if(type.equals("ZIP"))
            return new ZipExporter();

        throw new IllegalArgumentException("Unsupported export type: " + type);
    }
}
