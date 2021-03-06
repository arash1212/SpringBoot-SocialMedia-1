package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.common.UserFilesChecker;
import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.Users;
import com.salehi.socialmedia.model.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PostService {
    private PostRepository postRepository;
    private UsersService usersService;
    //
    Authentication authentication;


    @Autowired
    public PostService(PostRepository postRepository, UsersService usersService) {
        this.postRepository = postRepository;
        this.usersService = usersService;
    }

    public void save(Post post) {
        try {
            //
            UserFilesChecker<Post> userFilesChecker = new UserFilesChecker<>();
            //
            authentication = SecurityContextHolder.getContext().getAuthentication();
            byte[] fileBytes = post.getFilePosted().getBytes();
            //
            post.setAuthor(usersService.findUserByUsername(authentication.getName()));
            //
            String fileType = post.getFilePosted().getContentType();
            //checking if directories for uploading file exists.if not create new directories
            userFilesChecker.checkForUserFilePaths(post);
            //if uploaded file is an image file create new File in user 'images' folder
            String fileName = post.getAuthor().getUsername().split("@")[0] + new Date().getYear() + UUID.randomUUID() + post.getFilePosted().getOriginalFilename();
            if ((fileType.split("/")[0]).equals("image")) {
                Files.write(Paths.get(userFilesChecker.getImagesDirectory().getPath() + File.separator + fileName), fileBytes);
                post.setFilePath("/usersFiles/" + authentication.getName() + "/images/" + fileName);
                post.setPostHaveImage(true);
            }
            //if uploaded file is video file create new File in user 'videos' folder
            else if ((fileType.split("/")[0]).equals("video")) {
                Files.write(Paths.get(userFilesChecker.getVideosDirectory().getPath() + File.separator + fileName), fileBytes);
                post.setFilePath("/usersFiles/" + authentication.getName() + "/videos/" + fileName);
                post.setPostHaveVideo(true);
            }
            //
            post.setDateAdded(new Date());
            postRepository.save(post);
            //
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in sending post");
        }
    }

    public List<Post> getUserPosts(Users users) {
        return postRepository.getAllByAuthorOrderByDateAddedDesc(users);
    }



    public Post findById(long id) {
        return postRepository.findById(id);
    }

    public void saveLikes(Post post) {
        System.out.println(post.getId());
        postRepository.save(post);
    }

    /**
     * Method used for Testing for now
     * Method used to get user and All of it's friends Posts/Activity
     *
     * @return Method returns a list of all authenticated user and it's friends 'Post's
     */
    public List<Post> getAllFriendsActivity() {
        //
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        List<Users> friendList = usersService.getUserFriendsList();
        //
        List<Post> AllFriendsPosts = new ArrayList<>();
        //all friends Posts to the list
        for (Users user : friendList) {
            List<Post> userPosts = getUserPosts(user);
            //
            AllFriendsPosts.addAll(userPosts);
        }
        //add authenticated User Posts to the list
        Users authenticatedUser = usersService.findUserByUsername(authentication.getName());
        AllFriendsPosts.addAll(getUserPosts(authenticatedUser));
        //
        return AllFriendsPosts;
    }

}
