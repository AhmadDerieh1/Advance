package edu.najah.cap.data;

import edu.najah.cap.iam.UserType;

public interface UserMapFactory {
    void addUserToMap(UserType userType, String userName, MergeObject mergeObject);
}
