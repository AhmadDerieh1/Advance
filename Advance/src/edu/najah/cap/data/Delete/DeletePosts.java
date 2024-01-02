package edu.najah.cap.data;

import java.util.ArrayList;
import java.util.List;

import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;

public class DeletePosts implements Deletion {

    private IPostService postService;
    public DeletePosts(IPostService postService) {
        this.postService = postService;
    }
    @Override
    public void removeData(String userName) {
        try {
            List<Post> userPosts = postService.getPosts(userName);

            for (Post post : new ArrayList<>(userPosts)) {
                postService.deletePost(userName, post.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
