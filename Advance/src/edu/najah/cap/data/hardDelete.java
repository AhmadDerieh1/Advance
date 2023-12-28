package edu.najah.cap.data;
import edu.najah.cap.iam.UserProfile;

import java.util.HashSet;
import java.util.Set;

public class hardDelete {
    private static final Set<String> deletedUsernames = new HashSet<>();
    public void hardDeletionprocess(UserProfile userName){
        UserProfile userProfile = new UserProfile();
        deletedUsernames.add(userProfile.getUserName());

        userProfile.setFirstName(null);
        userProfile.setLastName(null);
        userProfile.setBuilding(null);
        userProfile.setCity(null);
        userProfile.setDepartment(null);
        userProfile.setCountry(null);
        userProfile.setUserType(null);
        userProfile.setStreet(null);
        userProfile.setRole(null);
        userProfile.setPostalCode(null);
        userProfile.setPhoneNumber(null);
        userProfile.setPassword(null);
        userProfile.setOrganization(null);
        userProfile.setUserName(null);
        userProfile.setEmail(null);

    }

    public static boolean isUsernameDeleted(String username) {
        return deletedUsernames.contains(username);
    }

}
