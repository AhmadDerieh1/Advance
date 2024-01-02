package edu.najah.cap.data;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.payment.IPayment;

import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;


public class DataFacadeImpl implements DataFacade {
    private static final Logger LOGGER = Logger.getLogger(DataFacadeImpl.class.getName());

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
        LOGGER.info("Strategy map initialized");
        strategyMap.put(UserType.PREMIUM_USER, new PremiumUserStrategy(paymentService, userActivityService));
        strategyMap.put(UserType.REGULAR_USER, new RegularUserStrategy(userActivityService));
        strategyMap.put(UserType.NEW_USER, new NewUserStrategy());
    }
    
    @Override
    public MergeObject getMergedData(String userName) {
        LOGGER.info("Getting merged data for user: " + userName);
        MergeObject mergeObject = new MergeObject();
        try {
            UserProfile userProfile = this.userService.getUser(userName);
            List<Post> posts = this.postService.getPosts(userName);
            if (userProfile != null) {
                mergeObject.setUserProfile(userProfile);
                mergeObject.setPosts(posts);
                UserType userType = userProfile.getUserType();
                UserTypeStrategy strategy = this.strategyMap.get(userType);
                if (strategy != null) {
                    strategy.collectUserData(mergeObject, userName);
                }

                this.mergeObjectMap.put(userType, mergeObject);
            } else {
                System.out.println("User profile not found for userName: " + userName);
            }
        } catch (SystemBusyException e) {
            System.err.println("System is currently busy. Please try again later. Details: " + e.getMessage());
            LOGGER.severe("System is currently busy. Details: " + e.getMessage());
        } catch (BadRequestException e) {
            System.err.println("Bad request for userName: " + userName + ". Details: " + e.getMessage());
            LOGGER.warning("Bad request for userName: " + userName + ". Details: " + e.getMessage());
        } catch (NotFoundException e) {
            System.err.println("User not found: " + userName + ". Details: " + e.getMessage());
            LOGGER.warning("User not found: " + userName + ". Details: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            LOGGER.severe("An unexpected error occurred: " + e.getMessage());
        }
        return mergeObject;
    }


    @Override
public Map<UserType, MergeObject> getMergeObjectMap() {
        LOGGER.info("Getting merge object map");
        return mergeObjectMap;
    /*
      |  Type   |  Obj  | 
      |         |       |
      |         |       |
    */
}
}