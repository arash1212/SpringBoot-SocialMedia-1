package com.salehi.socialmedia.model.common;

import com.salehi.socialmedia.model.entity.Post;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class UserFilesChecker<T> {
    //
    private File imagesDirectory;
    private File videosDirectory;
    //
    Authentication authentication;

    public File getImagesDirectory() {
        return imagesDirectory;
    }

    public UserFilesChecker<T> setImagesDirectory(File imagesDirectory) {
        this.imagesDirectory = imagesDirectory;
        return this;
    }

    public File getVideosDirectory() {
        return videosDirectory;
    }

    public UserFilesChecker<T> setVideosDirectory(File videosDirectory) {
        this.videosDirectory = videosDirectory;
        return this;
    }

    public void checkForUserFilePaths(T t) throws Exception {
        //
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
        authentication = SecurityContextHolder.getContext().getAuthentication();
        //
        //creating a new directory for each user with images and videos directories (inside our main directory)
        imagesDirectory = new File(classesPath + File.separator + "static" + File.separator + "usersFiles" + File.separator + authentication.getName() + File.separator + "images");
        videosDirectory = new File(classesPath + File.separator + "static" + File.separator + "usersFiles" + File.separator + authentication.getName() + File.separator + "videos");
        //
        for (File directory : Arrays.asList(imagesDirectory, videosDirectory)) {
            if (!directory.exists())
                directory.mkdirs();
        }
        //
    }
}
