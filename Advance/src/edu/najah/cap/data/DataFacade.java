package edu.najah.cap.data;

import java.util.Map;

import edu.najah.cap.iam.UserType;

public interface DataFacade {
    //The object contain restricted data
    MergeObject getMergedData(String userName);
    Map<UserType, MergeObject> getMergeObjectMap();
}