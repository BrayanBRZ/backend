package com.ifpr.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpr.backend.model.Profile;
import com.ifpr.backend.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    public Profile insert(Profile profile) {
        return repository.save(profile);
    }

    public Profile update(Profile profile) {
        
        Profile foundedprofile = findById(profile.getId());
        foundedprofile.setDescription(profile.getDescription());

        return repository.save(foundedprofile);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<Profile> findAll() {
        return repository.findAll();
    }

    public Profile findById(UUID id) {
        Profile profile = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return profile;
    }
}
