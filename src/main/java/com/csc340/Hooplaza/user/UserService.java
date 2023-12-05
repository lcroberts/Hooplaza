package com.csc340.Hooplaza.user;


import com.csc340.Hooplaza.community.CommunityRepository;
import com.csc340.Hooplaza.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private CommunityRepository comRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get all users
     *
     * @return the list of users
     */
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    /**
     * Find one user by ID.
     *
     * @param id user id to find
     * @return the user
     */
    public User getUser(long id) {
        return repo.getReferenceById(id);
    }

    /**
     * Delete user by ID.
     *
     * @param id user id to delete
     */
    public void deleteUser(long id) {
        repo.deleteById(id);
    }

    /**
     * Save user entry
     *
     * @param user user to save
     */
    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

    public void saveUser(User user) {
        repo.save(user);
    }

    /**
     * Update existing user.
     *
     * @param user updated user object
     */
    public void updateUser(User user) {
        User existing = repo.getReferenceById(user.getUserId());
        if (user.getEmail() != null) {
            existing.setEmail(user.getEmail());
        }
        if (user.getRole() != null) {
            existing.setRole(user.getRole());
        }
        if (user.getBookmarks() != null) {
            existing.setBookmarks(user.getBookmarks());
        }
        if (user.getCommunities() != null) {
            existing.setBookmarks(user.getBookmarks());
        }
        if (user.getModeratorOf() != null) {
            existing.setModeratorOf(user.getModeratorOf());
        }
        existing.setLastActiveCommunityId(user.getLastActiveCommunityId());
        existing.setActive(user.isActive());

        repo.save(existing);
    }

    public User getUserByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "not found"));
    }

}
