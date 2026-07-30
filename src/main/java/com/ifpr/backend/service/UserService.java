package com.ifpr.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpr.backend.model.Profile;
import com.ifpr.backend.model.User;
import com.ifpr.backend.model.UserProfile;
import com.ifpr.backend.repository.ProfileRepository;
import com.ifpr.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SendEmailService emailService;

    @Transactional
    public User insert(User user) {
        User userDb = repository.save(user);
        emailService.sendEmail(
            user.getEmail(),
            "Sucess",
            "Registration successful!");
        return userDb;
    }

    @Transactional
    public User update(User user) {
        
        User foundedUser = findById(user.getId());
        foundedUser.setName(user.getName());
        foundedUser.setEmail(user.getEmail());

        return repository.save(foundedUser);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return user;
    }

    @Transactional
    public User addProfile(Long userId, UUID profileId) {
        User user = findById(userId);
        Profile profile = findProfileById(profileId);

        boolean profileAlreadyAdded = user.getUserProfile().stream()
                .anyMatch(userProfile -> userProfile.getProfile() != null
                        && profileId.equals(userProfile.getProfile().getId()));

        if (!profileAlreadyAdded) {
            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user);
            userProfile.setProfile(profile);

            user.getUserProfile().add(userProfile);
        }

        return repository.save(user);
    }

    @Transactional
    public User removeProfile(Long userId, UUID profileId) {
        User user = findById(userId);
        findProfileById(profileId);

        boolean removed = user.getUserProfile().removeIf(userProfile -> userProfile.getProfile() != null
                && profileId.equals(userProfile.getProfile().getId()));

        if (!removed) {
            throw new RuntimeException("Profile não está vinculado ao usuário");
        }

        return repository.save(user);
    }

    @Transactional(readOnly = true)
    public List<Profile> findProfilesByUserId(Long userId) {
        User user = findById(userId);

        return user.getUserProfile().stream()
                .map(UserProfile::getProfile)
                .toList();
    }

    private Profile findProfileById(UUID profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile não encontrado"));

        return profile;
    }
}
