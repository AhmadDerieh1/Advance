package edu.najah.cap.data.export;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.payment.Transaction;
public class ExportPaymentsToPdf implements PrintDirectExporter {
    @Override
    public void printPdf(Document document, MergeObject user) throws DocumentException {
        List<Transaction> transactions = user.getTransactions(); 

        for (Transaction transaction : transactions) {
            document.add(new Paragraph("Payment Processed: " + transaction.getDescription()));
        }
    }
}

