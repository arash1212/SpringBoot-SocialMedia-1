package com.salehi.socialmedia.model.repository;

import com.salehi.socialmedia.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo save(UserInfo userInfo);
    UserInfo getById(long id);
}
