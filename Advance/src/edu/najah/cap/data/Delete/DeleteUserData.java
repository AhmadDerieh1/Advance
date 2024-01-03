package edu.najah.cap.data.Delete;

import java.util.logging.Logger;

import edu.najah.cap.iam.IUserService;

public class DeleteUserData implements Deletion {
    private static final Logger logger = Logger.getLogger(DeleteUserData.class.getName());
    private IUserService userService;

    public DeleteUserData(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void removeData(String userName) {
        try {
            userService.deleteUser(userName);
            logger.info("Successfully deleted user data for user: " + userName);
        } catch (Exception e) {
            // Handle any exceptions that may occur
            logger.warning("Exception occurred while deleting user data for user: " + userName);
            logger.warning(e.getMessage());
        }
    }
}
