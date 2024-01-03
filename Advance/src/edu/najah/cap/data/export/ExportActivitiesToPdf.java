package edu.najah.cap.data.export;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.data.MergeObject;

public class ExportActivitiesToPdf implements PrintDirectExporter {
    private static final Logger logger = Logger.getLogger(ExportActivitiesToPdf.class.getName());

    @Override
    public void printPdf(Document document, MergeObject user) {
        try {
            List<UserActivity> userActivities = user.getUserActivities();

            for (UserActivity activity : userActivities) {
                document.add(new Paragraph("Activity created: " + activity.getActivityType()));
                logger.log(Level.FINE, "Added activity to PDF: " + activity.getActivityType());
            }
            logger.log(Level.INFO, "Completed PDF export of user activities");
        } catch (DocumentException e) {
            logger.log(Level.SEVERE, "DocumentException in PDF export", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected exception in PDF export", e);
        }
    }

    @Override
    public String getDataType() {
        return "_Activities_";
    }
}
