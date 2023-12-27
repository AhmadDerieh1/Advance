package edu.najah.cap.export;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public interface DataExporter {
    void exportData(String userName) throws SystemBusyException, NotFoundException, BadRequestException;
}
