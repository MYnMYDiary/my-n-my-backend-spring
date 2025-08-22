package com.mynmy.springbackend.domain.user.repository;

import com.mynmy.springbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    @Modifying
    @Query("update User u set u.profileImage = :imageUrl where u.id = :userId")
    void updateProfileImage(@Param("userId") Long userId, @Param("imageUrl") String imageUrl);
}
