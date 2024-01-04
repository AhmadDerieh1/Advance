package edu.najah.cap.data.export;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.UserType;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

//basic class
public class DirectExporter implements DataExporter {
    private static final Logger logger = LoggerSetup.getLogger(); 

    Document document;
    public  DirectExporter()
    {
        document = new Document();
    }
  

    @Override
    public String exportData(MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException {
        String userName = user.getUserProfile().getUserName();
        UserType userType = user.getUserProfile().getUserType();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("UserName_" + userName.replaceAll("\\s+", "_") + ".pdf"));
            document.open();
            // Export User Profile Data
        
            PrintStrategyFactory factory = new PrintStrategyFactoryImpl();
            PrintStrategyCreator strategyCreator = factory.createPrintStrategy(userType);
            List<PrintDirectExporter> exporters = strategyCreator.createPrintStrategies();
            for (PrintDirectExporter exporter : exporters) {
                exporter.printPdf(document, user);
            }
            logger.log(Level.INFO, "Data export completed for user: " + userName);

        } catch (DocumentException | IOException e) {
            logger.log(Level.SEVERE, "Error during data export", e);
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
        return userName;
    }
}

