package edu.najah.cap.data.export;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public abstract class DataExporterDecorator implements DataExporter {
    protected DataExporter wrappedExporter;

    public DataExporterDecorator(DataExporter exporter) {
        this.wrappedExporter = exporter;
    }

    @Override
    public abstract String exportData(MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException;
}
