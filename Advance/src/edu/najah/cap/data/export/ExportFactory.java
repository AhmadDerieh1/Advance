package edu.najah.cap.data.export;

import java.util.logging.Logger;
import java.util.logging.Level;

public class ExportFactory {

    private static final Logger logger = Logger.getLogger(ExportFactory.class.getName());

    public DataExporter createExport(String type) {
        logger.log(Level.INFO, "Creating data exporter of type: " + type);

        if ("PDF".equals(type)) {
            logger.log(Level.INFO, "PDF exporter selected");
            return new DirectExporter();
        } else if ("ZIP".equals(type)) {
            logger.log(Level.INFO, "ZIP exporter selected");
            return new ZipExporter();
        } else {
            logger.log(Level.SEVERE, "Unsupported export type: " + type);
            throw new IllegalArgumentException("Unsupported export type: " + type);
        }
    }
}
