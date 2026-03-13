package com.bharatconnect.controller;

import com.bharatconnect.model.User;
import com.bharatconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String token = userService.login(email, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/verify-aadhar")
    public ResponseEntity<String> verifyAadhar(@PathVariable String id, @RequestParam String aadharNumber) {
        boolean verified = userService.verifyAadhar(id, aadharNumber);
        if (verified) {
            return ResponseEntity.ok("Aadhar verified!");
        }
        return ResponseEntity.badRequest().body("Verification failed");
    }

    @PostMapping("/{id}/suspend")
    public ResponseEntity<String> suspendUser(@PathVariable String id, @RequestParam String reason) {
        userService.suspendUser(id, reason);
        return ResponseEntity.ok("User suspended!");
    }

    @PostMapping("/{id}/appeal")
    public ResponseEntity<String> appealSuspension(@PathVariable String id, @RequestParam String evidence) {
        userService.appealSuspension(id, evidence);
        return ResponseEntity.ok("Appeal submitted!");
    }

    @PostMapping("/{id}/follow/{followId}")
    public ResponseEntity<String> followUser(@PathVariable String id, @PathVariable String followId) {
        userService.followUser(id, followId);
        return ResponseEntity.ok("Following!");
    }

    @PostMapping("/{id}/unfollow/{followId}")
    public ResponseEntity<String> unfollowUser(@PathVariable String id, @PathVariable String followId) {
        userService.unfollowUser(id, followId);
        return ResponseEntity.ok("Unfollowed!");
    }
}
