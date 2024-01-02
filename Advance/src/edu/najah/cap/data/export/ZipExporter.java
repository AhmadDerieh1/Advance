package edu.najah.cap.data.export;



import com.itextpdf.text.Document;

import com.itextpdf.text.pdf.PdfWriter;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.UserType;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipExporter implements DataExporter {
    private Document document;

    public ZipExporter() {
        document = new Document();
    }

    @Override
    public void exportData(MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException {
            String userName=user.getUserProfile().getUserName();
            UserType userType=user.getUserProfile().getUserType();
            byte[] userDataPdf = null;
            byte[] postsPdf = null;
            byte[] activitiesPdf = null;
            byte[] paymentsPdf = null;
        try {
            // Check if the user exists before exporting data
            if (!userExists(userName)) {
                throw new NotFoundException("User does not exist: " + userName);
            }
       
            if (userType.equals(UserType.NEW_USER)) {
                userDataPdf = creatUserDataPdf(document,user);
                postsPdf = creatPostsPdf(document,user);
            } else if (userType.equals(UserType.REGULAR_USER)) {
                userDataPdf = creatUserDataPdf(document,user);
                postsPdf = creatPostsPdf(document,user);
                activitiesPdf = creatActivitiesPdf(document,user);
            } else if (userType.equals(UserType.PREMIUM_USER)) {
                userDataPdf = creatUserDataPdf(document,user);
                postsPdf = creatPostsPdf(document,user);
                activitiesPdf = creatActivitiesPdf(document,user);
                paymentsPdf = creatPaymentsPdf(document,user);
            }
      
           // Zip the files
        
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("exported_data.zip"))) {
                addPdfToZip(zipOutputStream, "userData_" + userName.replaceAll("\\s+", "_") + ".pdf",userDataPdf);

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
         finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    
    }



    // Add this method to check if the user exists
    private boolean userExists(String userName) {
        // Implement logic to check if the user exists in your system
        // Return true if the user exists, false otherwise
        return true; // Replace with actual logic
    }
    private byte[] creatUserDataPdf(Document document , MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

           PrintDirectExporter exportUserDataToPdf= new ExportUserProfileDataToPdf();
           exportUserDataToPdf.printPdf(document, user);
                 

        } catch (Exception e) {
            e.printStackTrace();  // Use a logger instead of printStackTrace
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] creatPostsPdf(Document document , MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Export Posts Data
            PrintDirectExporter exportPostsToPdf= new ExportPostsToPdf();
            exportPostsToPdf.printPdf(document, user);

        } catch (Exception e) {
            e.printStackTrace();  // Use a logger instead of printStackTrace
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] creatActivitiesPdf(Document document , MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Export Activities Data
             PrintDirectExporter exportActivitiesToPdf= new ExportActivitiesToPdf();
            exportActivitiesToPdf.printPdf(document, user);

        } catch (Exception e) {
            e.printStackTrace();  // Use a logger instead of printStackTrace
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] creatPaymentsPdf(Document document,MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
       

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            PrintDirectExporter exportPaymentsToPdf= new ExportPaymentsToPdf ();
            exportPaymentsToPdf.printPdf(document, user);

   

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }
    

    private void addPdfToZip(ZipOutputStream zipOutputStream, String entryName, byte[] pdfBytes) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(pdfBytes);
        zipOutputStream.closeEntry();
    }
}


