package edu.najah.cap.data.Delete;

import edu.najah.cap.iam.IUserService;

public class DeleteUserData implements Deletion{

    private IUserService userService;

    public DeleteUserData(IUserService userService) {
        this.userService = userService;
    }
     @Override
    public void removeData(String userName) {
      try {
          userService.deleteUser(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}