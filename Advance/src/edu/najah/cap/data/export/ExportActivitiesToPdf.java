package edu.najah.cap.data.export;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.data.MergeObject;
public class ExportActivitiesToPdf implements PrintDirectExporter {
    @Override
    public void printPdf(Document document, MergeObject user) throws DocumentException {
        List<UserActivity> userActivities = user.getUserActivities(); 

        for (UserActivity activity : userActivities) {
            document.add(new Paragraph("Activity created: " + activity.getActivityType()));
  
        }
    }
}

