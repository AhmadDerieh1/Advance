package edu.najah.cap.data.export;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.posts.Post;

public class ExportPostsToPdf implements PrintDirectExporter {

    private static final Logger logger = Logger.getLogger(ExportPostsToPdf.class.getName());

    @Override
    public void printPdf(Document document, MergeObject user) {
        try {
            List<Post> posts = user.getPosts();
            logger.log(Level.INFO, "Starting PDF export of posts for user: " + user.getUserProfile().getUserName());

            for (Post post : posts) {
                document.add(new Paragraph("Post Title: " + post.getTitle()));
                logger.log(Level.FINE, "Added post to PDF: " + post.getTitle());
            }

            logger.log(Level.INFO, "Completed PDF export of posts");
        } catch (DocumentException e) {
            logger.log(Level.SEVERE, "Error occurred while exporting posts to PDF", e);
            // Additional exception handling logic can be added here
        }
    }

    @Override
    public String getDataType() {
        return "_Posts";
    }
}
