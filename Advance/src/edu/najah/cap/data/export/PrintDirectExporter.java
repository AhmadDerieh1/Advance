package edu.najah.cap.data.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import edu.najah.cap.data.MergeObject;

public interface PrintDirectExporter {
    void printPdf(Document document, MergeObject user) throws DocumentException;
}

/* try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(userName + "_data.zip"))) {
                for (PrintDirectExporter exporter : exporters) {
                    String pdfFileName = exporter.getPdfFileName(); // تأكد من أن لديك هذه الطريقة في PrintDirectExporter
                    byte[] pdfContent = exporter.exportToPdf(user); // تأكد من أن لديك هذه الطريقة في PrintDirectExporter
                    addPdfToZip(zipOutputStream, pdfFileName, pdfContent);
                } */