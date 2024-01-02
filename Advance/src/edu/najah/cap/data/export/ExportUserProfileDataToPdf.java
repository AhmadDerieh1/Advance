package edu.najah.cap.data.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.iam.UserProfile;

public class ExportUserProfileDataToPdf implements PrintDirectExporter {
    @Override
    public void printPdf(Document document, MergeObject user) throws DocumentException {
        UserProfile userProfile = user.getUserProfile();
        document.add(new Paragraph("UserName: " + userProfile.getUserName()));
        document.add(new Paragraph("First Name: " + userProfile.getFirstName()));
        document.add(new Paragraph("Last Name: " + userProfile.getLastName()));
        document.add(new Paragraph("Phone number: " + userProfile.getPhoneNumber()));
        document.add(new Paragraph("Email: " + userProfile.getEmail()));
        document.add(new Paragraph("Password: " + userProfile.getPassword())); 
        document.add(new Paragraph("Role: " + userProfile.getRole()));
        document.add(new Paragraph("Department: " + userProfile.getDepartment()));
        document.add(new Paragraph("Organization: " + userProfile.getOrganization()));
        document.add(new Paragraph("Country: " + userProfile.getCountry()));
        document.add(new Paragraph("City: " + userProfile.getCity()));
        document.add(new Paragraph("Street: " + userProfile.getStreet()));
        document.add(new Paragraph("Post code: " + userProfile.getPostalCode()));
        document.add(new Paragraph("Building: " + userProfile.getBuilding()));
        document.add(new Paragraph("User type: " + userProfile.getUserType()));
    }
}
