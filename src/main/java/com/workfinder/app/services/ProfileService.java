package com.workfinder.app.services;

import com.workfinder.app.models.Profile;
import com.workfinder.app.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile addProfile(Profile Profile){
        return profileRepository.save(Profile);
    }

    public Profile updateProfile(Profile profile , String id){
        Optional<Profile> optionalExistedProfile = profileRepository.findById(id);
        if(optionalExistedProfile.isPresent()){
            Profile existedProfile = optionalExistedProfile.get();
            existedProfile.setName(profile.getName());
            return profileRepository.save(existedProfile);
        }
        else {
            return null;
        }
    }

    public void deleteProfile(String id){
        profileRepository.deleteById(id);
    }

    public List<Profile> getAllProfiles(){

        return profileRepository.findAll();

    }

    public Profile getProfileById(String id){
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if(optionalProfile.isPresent()){
            return optionalProfile.get();
        }
        else {
            return null;
        }

    }
}
