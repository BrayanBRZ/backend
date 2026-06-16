package com.ifpr.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ifpr.backend.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    
}