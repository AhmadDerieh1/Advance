package edu.najah.cap.data.export;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.posts.Post;
public class ExportPostsToPdf implements PrintDirectExporter {
    @Override
    public void printPdf(Document document, MergeObject user) throws DocumentException {
        List<Post> posts = user.getPosts(); 

        for (Post post : posts) {
            document.add(new Paragraph("Post Title: " + post.getTitle()));
        }
    }
    @Override
    public String getDataType() {
        return "_Posts";
    }
}


