package edu.najah.cap.data.export;



import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.UserType;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipExporter implements DataExporter {
    private Document document;

    

    @Override
    public void exportData(MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException {
            String userName=user.getUserProfile().getUserName();
            UserType userType=user.getUserProfile().getUserType();
            byte[] pdfContent = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
            String timestamp = dateFormat.format(new Date());
          
        try {
            // Check if the user exists before exporting data
            if (!userExists(userName)) {
                throw new NotFoundException("User does not exist: " + userName);
            }
        PrintStrategyFactory factory = new PrintStrategyFactoryImpl();
        PrintStrategyCreator strategyCreator = factory.createPrintStrategy(userType);
      
        List<PrintDirectExporter> exporters = strategyCreator.createPrintStrategies();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(userName + "_" + timestamp + "_exported_data.zip"))) {

            for (PrintDirectExporter dataAddedDocument : exporters) {
                    // dataAddedDocument : do add to document
                pdfContent = createPdf(document, user, dataAddedDocument);
                String pdfFileName = dataAddedDocument.getDataType();
                addPdfToZip(zipOutputStream, pdfFileName + userName.replaceAll("\\s+", "_") + ".pdf", pdfContent);
            }
       
        }catch (IOException e) {
                e.printStackTrace();  // Handle the exception appropriately ( log it)
            }
        } catch (SystemBusyException | NotFoundException | BadRequestException e) {
            // Handle exceptions or log them
            e.printStackTrace();
        }
        //To log information about each export in a text file called export_log.txt
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("export_log.txt", true)))) {
        out.println("Exported " + timestamp + " for user " + userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Add this method to check if the user exists
    private boolean userExists(String userName) {
        // Implement logic to check if the user exists in your system
        // Return true if the user exists, false otherwise
        return true; // Replace with actual logic
    }
    public byte[] createPdf(Document document, MergeObject user, PrintDirectExporter dataAddedDocument) throws SystemBusyException, NotFoundException, BadRequestException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document = new Document(); 
    
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            dataAddedDocument.printPdf(document, user);
        } catch (Exception e) {
            e.printStackTrace();  // Use a logger instead of printStackTrace
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    
        return byteArrayOutputStream.toByteArray();
    }
    
    
    //zip then compress PDF files
    private void addPdfToZip(ZipOutputStream zipOutputStream, String entryName, byte[] pdfBytes) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(pdfBytes);
        zipOutputStream.closeEntry();
    }
}
