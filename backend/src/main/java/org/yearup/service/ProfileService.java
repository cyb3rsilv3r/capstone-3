package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    //Add method service path for searching and updating for controller to call

    public Profile getByUserId(int userId)
    {
        return profileRepository.findByUserId(userId);
    }

    public Profile update(Profile profile)
    {
        return profileRepository.save(profile);
    }
}
