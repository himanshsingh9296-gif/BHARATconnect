package com.bharatconnect.service;

import com.bharatconnect.model.User;
import com.bharatconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return "JWT_TOKEN_HERE";
        }
        return null;
    }

    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User u = existingUser.get();
            if (user.getName() != null) u.setName(user.getName());
            if (user.getBio() != null) u.setBio(user.getBio());
            if (user.getProfilePicture() != null) u.setProfilePicture(user.getProfilePicture());
            return userRepository.save(u);
        }
        return null;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public boolean verifyAadhar(String id, String aadharNumber) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setAadharNumber(aadharNumber);
            u.setAadharVerified(true);
            userRepository.save(u);
            return true;
        }
        return false;
    }

    public void suspendUser(String id, String reason) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setAccountStatus("SUSPENDED");
            userRepository.save(u);
        }
    }

    public void appealSuspension(String id, String evidence) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setAccountStatus("APPEAL_PENDING");
            userRepository.save(u);
        }
    }

    public void followUser(String userId, String followId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            u.setFollowingCount(u.getFollowingCount() + 1);
            userRepository.save(u);
        }
        
        Optional<User> followUser = userRepository.findById(followId);
        if (followUser.isPresent()) {
            User u = followUser.get();
            u.setFollowersCount(u.getFollowersCount() + 1);
            userRepository.save(u);
        }
    }

    public void unfollowUser(String userId, String followId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            u.setFollowingCount(u.getFollowingCount() - 1);
            userRepository.save(u);
        }
        
        Optional<User> followUser = userRepository.findById(followId);
        if (followUser.isPresent()) {
            User u = followUser.get();
            u.setFollowersCount(u.getFollowersCount() - 1);
            userRepository.save(u);
        }
    }
              }
