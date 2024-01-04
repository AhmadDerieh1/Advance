package edu.najah.cap.data.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;

public class DeletePosts implements Deletion {
    private static final Logger logger = LoggerSetup.getLogger(); 
    private IPostService postService;

    public DeletePosts(IPostService postService) {
        this.postService = postService;
    }

    @Override
    public boolean removeData(String userName, MergeObject mergeObject) {
        boolean isRemovedSuccessfully = false;
        try {
            List<Post> userPosts = postService.getPosts(userName);
            for (Post post : new ArrayList<>(userPosts)) {
                postService.deletePost(userName, post.getId());
                mergeObject.getPosts().remove(post);
            }
            mergeObject.setPosts(new ArrayList<>()); 
            logger.info("Successfully removed posts for user: " + userName);
            isRemovedSuccessfully = true;
        } catch (Exception e) {
            logger.warning("Exception occurred while removing posts for user: " + userName);
            logger.warning(e.getMessage());
        }
        return isRemovedSuccessfully;
    }
}
 