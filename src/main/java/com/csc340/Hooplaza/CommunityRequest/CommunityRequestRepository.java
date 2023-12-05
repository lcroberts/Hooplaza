package com.csc340.Hooplaza.CommunityRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommunityRequestRepository extends JpaRepository<CommunityRequest, Long> {

    Optional<CommunityRequest> findById(long id);
    Optional<CommunityRequest> findByLocationId(String locationId);
}
