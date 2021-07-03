package com.salehi.socialmedia.model.service;

import com.salehi.socialmedia.model.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AuthoritiesService extends JpaRepository<Authorities,Long> {
     Authorities save(Authorities authorities);
}
