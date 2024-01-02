package edu.najah.cap.data.export;

import edu.najah.cap.iam.UserType;

public class PrintStrategyFactoryImpl implements PrintStrategyFactory {
    @Override
    public PrintStrategyCreator createPrintStrategy(UserType userType) {
        if (userType.equals(UserType.NEW_USER)) {
            return new NawPrintStrategyCreator();
        } else if (userType.equals(UserType.REGULAR_USER)) {
            return new RegularPrintStrategyCreator();
        } else if (userType.equals(UserType.PREMIUM_USER)) {
            return new PremiumPrintStrategyCreator();
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }
    }
}
