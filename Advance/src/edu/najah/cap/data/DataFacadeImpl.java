package edu.najah.cap.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.Transaction;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;


public class DataFacadeImpl implements DataFacade {
    // D
    private final IUserService userService;
    private final IPostService postService;
    private final IPayment paymentService;
    private final IUserActivityService userActivityService;
    private final Map<UserType, MergeObject> mergeObjectMap= new HashMap<>();
     private Map<UserType, UserTypeStrategy> strategyMap = new HashMap<>();

    public DataFacadeImpl(IUserService userService, IPostService postService, IPayment paymentService, IUserActivityService userActivityService) {
        this.userService = userService;
        this.postService = postService;
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
         initializeStrategyMap();
    }
    private void initializeStrategyMap() {
        strategyMap.put(UserType.PREMIUM_USER, new PremiumUserStrategy(paymentService, userActivityService));
        strategyMap.put(UserType.REGULAR_USER, new RegularUserStrategy(userActivityService));
        strategyMap.put(UserType.NEW_USER, new NewUserStrategy());
    }
    
    @Override
    public MergeObject getMergedData(String userName) {
        MergeObject mergeObject = new MergeObject();
        try {
            UserProfile userProfile = userService.getUser(userName);
            List<Post> posts = postService.getPosts(userName);
    
            if (userProfile != null) {
                mergeObject.setUserProfile(userProfile);
                mergeObject.setPosts(posts);
    
                UserType userType = userProfile.getUserType();
                UserTypeStrategy strategy = strategyMap.get(userType);
                if (strategy != null) {
                    strategy.collectUserData(mergeObject, userName);
                }
                mergeObjectMap.put(userType, mergeObject);
            } else {
                System.out.println("userProfile=null !");
            }
        } catch (NotFoundException | SystemBusyException | BadRequestException e) {
           
        }
        return mergeObject;
    }
    
@Override
public Map<UserType, MergeObject> getMergeObjectMap() {
    return mergeObjectMap;
    /*
      |  Type   |  Obj  | 
      |         |       |
      |         |       |
    */
}


}

