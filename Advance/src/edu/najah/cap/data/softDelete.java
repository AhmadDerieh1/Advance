package edu.najah.cap.data;
import edu.najah.cap.iam.UserProfile;
public class softDelete {
    public void softDeletionprocess(UserProfile userName){
        UserProfile userProfile = new UserProfile();
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
    }
}
