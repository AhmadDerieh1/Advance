package edu.najah.cap.data.export;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


import edu.najah.cap.data.LoggerSetup;

import java.util.logging.Level;

public class PremiumPrintStrategyCreator implements PrintStrategyCreator {

     private static final Logger logger = LoggerSetup.getLogger(); 



    @Override
    public List<PrintDirectExporter> createPrintStrategies() {
        logger.log(Level.INFO, "Creating premium print strategies for document export");

        List<PrintDirectExporter> strategies = Arrays.asList(
                new ExportUserProfileDataToPdf(),
                new ExportPostsToPdf(),
                new ExportActivitiesToPdf(),
                new ExportPaymentsToPdf()
        );

        logger.log(Level.INFO, "Successfully created " + strategies.size() + " premium print strategies");

        return strategies;
    }
}
