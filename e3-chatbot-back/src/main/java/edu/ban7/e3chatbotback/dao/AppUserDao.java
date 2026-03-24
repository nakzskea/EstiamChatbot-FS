package edu.ban7.e3chatbotback.dao;

import edu.ban7.e3chatbotback.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserDao extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByEmail(String email);
}
