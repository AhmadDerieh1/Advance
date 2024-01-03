package edu.najah.cap.data.export;

import java.util.logging.Logger;
import java.util.logging.Level;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.iam.UserProfile;

public class ExportUserProfileDataToPdf implements PrintDirectExporter {

    private static final Logger logger = Logger.getLogger(ExportUserProfileDataToPdf.class.getName());

    @Override
    public void printPdf(Document document, MergeObject user) {
        UserProfile userProfile = user.getUserProfile();

        try {
            logger.log(Level.INFO, "Starting PDF export of user profile data for user: " + userProfile.getUserName());

            // Add user profile data to the document
            document.add(new Paragraph("UserName: " + userProfile.getUserName()));
            document.add(new Paragraph("First Name: " + userProfile.getFirstName()));
            document.add(new Paragraph("Last Name: " + userProfile.getLastName()));
            document.add(new Paragraph("Phone number: " + userProfile.getPhoneNumber()));
            document.add(new Paragraph("Email: " + userProfile.getEmail()));
            document.add(new Paragraph("Password: " + userProfile.getPassword()));  // Note: Exporting passwords is not recommended
            document.add(new Paragraph("Role: " + userProfile.getRole()));
            document.add(new Paragraph("Department: " + userProfile.getDepartment()));
            document.add(new Paragraph("Organization: " + userProfile.getOrganization()));
            document.add(new Paragraph("Country: " + userProfile.getCountry()));
            document.add(new Paragraph("City: " + userProfile.getCity()));
            document.add(new Paragraph("Street: " + userProfile.getStreet()));
            document.add(new Paragraph("Post code: " + userProfile.getPostalCode()));
            document.add(new Paragraph("Building: " + userProfile.getBuilding()));
            document.add(new Paragraph("User type: " + userProfile.getUserType()));

            logger.log(Level.INFO, "Completed PDF export of user profile data");
        } catch (DocumentException e) {
            logger.log(Level.SEVERE, "Error occurred while exporting user profile data to PDF", e);
        }
    }

    @Override
    public String getDataType() {
        return "userData";
    }
}
