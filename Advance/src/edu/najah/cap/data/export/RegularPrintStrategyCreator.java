package edu.najah.cap.data.export;
import java.util.Arrays;
import java.util.List;
public class RegularPrintStrategyCreator implements PrintStrategyCreator {
    @Override
    public List<PrintDirectExporter> createPrintStrategies() {
        return Arrays.asList(
            new ExportUserProfileDataToPdf(),
            new ExportPostsToPdf(),
            new ExportActivitiesToPdf()
        );
    }
}
