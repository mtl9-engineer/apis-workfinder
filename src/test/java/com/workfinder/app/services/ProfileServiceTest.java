package com.workfinder.app.services;

import com.workfinder.app.models.Profile;
import com.workfinder.app.repositories.ProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @InjectMocks
    ProfileService profileService;

    @Mock
    ProfileRepository profileRepository;
    

    private Profile profile;
    @BeforeEach
    public  void setUp(){
        profile = new Profile();

        profile.setId("profileId");
        profile.setName("Blockchain Developer");
       
    }

    @Test
    public void addProfile(){
        Mockito.when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        Profile expectedProfile = profileService.addProfile(profile);

        Assertions.assertEquals("profileId",expectedProfile.getId());
        Assertions.assertEquals("Blockchain Developer",expectedProfile.getName());
    }

    @Test
    public void updateProfile(){
        Profile profile2 = new Profile();
        profile2.setName("Mobile Developer");

        Mockito.when(profileRepository.findById("profileId")).thenReturn(Optional.of(profile));
        Mockito.when(profileRepository.save(any(Profile.class))).thenReturn(profile2);

        Profile updatedProfile = profileService.updateProfile(profile2,"profileId");

        Assertions.assertEquals("Mobile Developer",updatedProfile.getName());
    }


    @Test
    public void getProfileById(){
        Mockito.when(profileRepository.findById("profileId")).thenReturn(Optional.of(profile));

        Profile expectedProfile = profileService.getProfileById("profileId");

        Assertions.assertEquals("Blockchain Developer",expectedProfile.getName());

    }

    @Test
    public void getAllProfiles(){
        List<Profile> profiles =  new ArrayList<>();
        profiles.add(profile);

        Mockito.when(profileRepository.findAll()).thenReturn(profiles);

        List<Profile> expectedProfiles = profileService.getAllProfiles();
        Assertions.assertEquals(1,expectedProfiles.size());
    }

}
