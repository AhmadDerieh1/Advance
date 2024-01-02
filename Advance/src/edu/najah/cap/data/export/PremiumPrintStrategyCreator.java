package edu.najah.cap.data.export;

import java.util.Arrays;
import java.util.List;

public class PremiumPrintStrategyCreator implements PrintStrategyCreator {
    @Override
    public List<PrintDirectExporter> createPrintStrategies() {
        return Arrays.asList(
            new ExportUserProfileDataToPdf(),
            new ExportPostsToPdf(),
            new ExportActivitiesToPdf(),
            new ExportPaymentsToPdf()
        );
    }
}


