package com.bharatconnect.repository;

import com.bharatconnect.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Email se user dhundo
    Optional<User> findByEmail(String email);

    // Aadhar number se user dhundo
    Optional<User> findByAadharNumber(String aadharNumber);

    // Account status ke hisaab se users dhundo
    List<User> findByAccountStatus(String accountStatus);

    // Role ke hisaab se users dhundo
    List<User> findByRole(String role);

    // Verified creators dhundo
    List<User> findByCreatorVerifiedTrue();
}
