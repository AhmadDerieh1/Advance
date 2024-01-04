package edu.najah.cap.data.export;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.payment.Transaction;

public class ExportPaymentsToPdf implements PrintDirectExporter {

    private static final Logger logger = LoggerSetup.getLogger(); 


    @Override
    public void printPdf(Document document, MergeObject user) {
        try {
            List<Transaction> transactions = user.getTransactions();

            logger.log(Level.INFO, "Starting PDF export of payment transactions for user: " + user.getUserProfile().getUserName());

            for (Transaction transaction : transactions) {
                document.add(new Paragraph("Payment Processed: " + transaction.getDescription()));
                logger.log(Level.FINE, "Added transaction to PDF: " + transaction.getDescription());
            }

            logger.log(Level.INFO, "Completed PDF export of payment transactions");
        } catch (DocumentException e) {
            logger.log(Level.SEVERE, "Error occurred while exporting payment transactions to PDF", e);
        }
    }

    @Override
    public String getDataType() {
        return "Payments_";
    }
}