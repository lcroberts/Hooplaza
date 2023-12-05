package com.csc340.Hooplaza.community;

import com.csc340.Hooplaza.CommunityRequest.CommunityRequest;
import com.csc340.Hooplaza.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findById(long id);
    Optional<Community> findByLocationId(String locationId);

    @Query("SELECT c FROM Community c WHERE c.name LIKE %?1% ORDER BY c.communityId DESC")
    public List<Community> search(String keyword);
}
