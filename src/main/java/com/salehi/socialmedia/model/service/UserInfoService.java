package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.common.UserFilesChecker;
import com.salehi.socialmedia.model.entity.Post;
import com.salehi.socialmedia.model.entity.UserInfo;
import com.salehi.socialmedia.model.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
public class UserInfoService {

    private UserInfoRepository userInfoRepository;
    private Authentication authentication;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo save(UserInfo userInfo) {
        try {
            UserInfo oldUserInfo = userInfoRepository.getById(userInfo.getId());
            //
            UserFilesChecker<UserInfo> userFilesChecker = new UserFilesChecker<>();
            //
            authentication = SecurityContextHolder.getContext().getAuthentication();
            //
            //checking if directories for uploading file exists.if not create new directories
            userFilesChecker.checkForUserFilePaths(userInfo);

            if (userInfo.getProfilePictureFile() != null) {
                //filename
                String profilePictureFileName = authentication.getName() + "_profilePicture.png";
                //
                String profilePictureFileType = userInfo.getProfilePictureFile().getContentType();
                //save profile picture if it's not null and check if it's an image file before saving
                if (userInfo.getProfilePictureFile() != null && (profilePictureFileType.split("/")[0]).equals("image")) {
                    byte[] profilePictureFileBytes = userInfo.getProfilePictureFile().getBytes();
                    Files.write(Paths.get(userFilesChecker.getImagesDirectory().getPath() + File.separator + profilePictureFileName), profilePictureFileBytes);
                    userInfo.setProfilePictureFileAddress("/usersFiles/" + authentication.getName() + "/images/" + profilePictureFileName);
                } else {
                    //if file is not an image file
                    userInfo.setProfilePictureFileAddress(oldUserInfo.getProfilePictureFileAddress());
                }
            } else {
                userInfo.setProfilePictureFileAddress("/default-images/default-profile-picture.png");
            }

            if (userInfo.getProfileBackgroundPictureFile() != null) {
                //file name
                String profileBackgroundPictureFileName = authentication.getName() + "_profileBackgroundPicture.png";
                //
                String profileBackgroundPictureFileType = userInfo.getProfilePictureFile().getContentType();
                //save profile background picture if it's not null and check if it's an image file before saving
                if (userInfo.getProfileBackgroundPictureFile() != null && (profileBackgroundPictureFileType.split("/")[0]).equals("image")) {
                    byte[] profileBackgroundPictureFileBytes = userInfo.getProfilePictureFile().getBytes();
                    Files.write(Paths.get(userFilesChecker.getImagesDirectory().getPath() + File.separator + profileBackgroundPictureFileName), profileBackgroundPictureFileBytes);
                    userInfo.setProfileBackgroundPictureFileAddress("/usersFiles/" + authentication.getName() + "/images/" + profileBackgroundPictureFileName);
                } else {
                    //if file is not an image file
                    userInfo.setProfilePictureFileAddress(oldUserInfo.getProfilePictureFileAddress());
                }
            } else {
                userInfo.setProfileBackgroundPictureFileAddress("/default-images/default-profile-background-image.png");
            }
            //
            return userInfoRepository.save(userInfo);
            //
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in sending post");
            return null;
        }
    }

    public UserInfo getById(long id) {
        return userInfoRepository.getById(id);
    }

}
