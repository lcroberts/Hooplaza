package com.csc340.Hooplaza.community;

import com.csc340.Hooplaza.CommunityRequest.CommunityRequest;
import com.csc340.Hooplaza.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findById(long id);
    Optional<Community> findByLocationId(String locationId);
}
