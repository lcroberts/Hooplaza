package com.csc340.Hooplaza.user;


import com.csc340.Hooplaza.community.CommunityRepository;
import com.csc340.Hooplaza.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private CommunityRepository comRepo;

    @Autowired
    private PostRepository postRepo;

}
