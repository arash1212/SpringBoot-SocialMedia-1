package com.salehi.socialmedia.model.service;

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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private PostRepository postRepository;
    private UsersService usersService;
    //
    Authentication authentication;
    //
    File imagesDirectory;
    File videosDirectory;

    @Autowired
    public PostService(PostRepository postRepository, UsersService usersService) {
        this.postRepository = postRepository;
        this.usersService = usersService;
    }

    public void save(Post post) {
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            byte[] fileBytes = post.getFilePosted().getBytes();
            //
            post.setAuthor(usersService.findUserByUsername(authentication.getName()));
            //
            String fileType = post.getFilePosted().getContentType();
            //checking if directories for uploading file exists.if not create new directories
            checkForUserFilePaths(post);
            //if uploaded file is an image file create new File in user 'images' folder
            String fileName = post.getAuthor().getUsername().split("@")[0] + new Date().getYear() + UUID.randomUUID() + post.getFilePosted().getOriginalFilename();
            if ((fileType.split("/")[0]).equals("image")) {
                Files.write(Paths.get(imagesDirectory.getPath() + File.separator + fileName), fileBytes);
                post.setFilePath("usersFiles/" + authentication.getName() + "/images/" + fileName);
            }
            //if uploaded file is video file create new File in user 'videos' folder
            else if ((fileType.split("/")[0]).equals("video")) {
                Files.write(Paths.get(videosDirectory.getPath() + File.separator  + fileName), fileBytes);
                post.setFilePath("usersFiles/" + authentication.getName() + "/videos/" + fileName);
            }
            //
            System.out.println(post.getFilePath());
            postRepository.save(post);
            //
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in sending post");
        }
    }

    public List<Post> getUserPosts(Users users) {
        return postRepository.getAllByAuthor(users);
    }

    /**
     * @param post getting "users" from "post" to check if directories for the user who's trying to send post is already exists or not to store the file
     */
    public void checkForUserFilePaths(Post post) throws Exception {
        //
        System.out.println("class path : " + ResourceUtils.getFile("classpath:"));
        String classesPath = ResourceUtils.getFile("classpath:").getAbsolutePath();
        //creating main (userFiles) directory to store user files at
        File usersFilesDirectory = new File(classesPath + File.separator + "static" + File.separator + "usersFiles");
        if (!usersFilesDirectory.exists()) {
            Files.createDirectories(usersFilesDirectory.toPath());
            System.out.println("main directory created");
        }
        //
        String path = usersFilesDirectory.getPath();
        //
        //creating a new directory for each user with images and videos directories (inside our main directory)
        imagesDirectory = new File(classesPath + File.separator + "static" + File.separator + "usersFiles" + File.separator + post.getAuthor().getUsername() + File.separator + "images");
        videosDirectory = new File(classesPath + File.separator + "static" + File.separator + "usersFiles" + File.separator + post.getAuthor().getUsername() + File.separator + "videos");
        //
        for (File directory : Arrays.asList(imagesDirectory, videosDirectory)) {
            if (!directory.exists())
                directory.mkdirs();
        }
        //
    }
}
