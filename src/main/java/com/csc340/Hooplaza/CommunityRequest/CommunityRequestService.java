package com.csc340.Hooplaza.CommunityRequest;


import com.csc340.Hooplaza.community.CommunityRepository;
import com.csc340.Hooplaza.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityRequestService {

    @Autowired
    private CommunityRequestRepository reqRepo;
    @Autowired
    private CommunityRepository commRepo;
    @Autowired
    private UserRepository userRepo;

    /**
     * Get all users
     *
     * @return the list of users
     */
    public List<CommunityRequest> getAllRequests() {
        return reqRepo.findAll();
    }

    /**
     * Gets a request by its id
     *
     * @param reqId id of request
     * @return the community request
     */
    public CommunityRequest getById(long reqId) {
        return reqRepo.getReferenceById(reqId);
    }

    /**
     * Will delete a community request by its id
     *
     * @param reqId id of request
     */
    public void deleteById(long reqId) {
        reqRepo.deleteById(reqId);
    }

    /**
     * Saves a new community request
     *
     * @param request request to save
     */
    public void saveCommunityRequest(CommunityRequest request) {
        reqRepo.save(request);
    }

    /**
     * Will attempt to find a location given its location id
     *
     * @param locationId id of given locaiton
     * @return community with matching location if it exists
     */
    public Optional<CommunityRequest> getRequestByLocation(String locationId) {
        return reqRepo.findByLocationId(locationId);
    }

    /**
     * Updates an existing request
     *
     * @param request updated request object
     */
    public void updateRequest(CommunityRequest request) {
        CommunityRequest existing = reqRepo.getReferenceById(request.getRequestId());
        if (request.getBody() != null) {
            existing.setBody(request.getBody());
        }
        if (request.getName() != null) {
            existing.setName(request.getName());
        }
        if (request.getLocationId() != null) {
            existing.setLocationId(request.getLocationId());
        }
        existing.setRequestActive(request.isRequestActive());
        existing.setRequestAccepted(request.isRequestAccepted());
        reqRepo.save(existing);
    }
}
