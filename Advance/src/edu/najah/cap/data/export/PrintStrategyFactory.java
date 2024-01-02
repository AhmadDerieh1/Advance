package edu.najah.cap.data.export;

import edu.najah.cap.iam.UserType;

public interface PrintStrategyFactory {
    PrintStrategyCreator createPrintStrategy(UserType userType);
}
