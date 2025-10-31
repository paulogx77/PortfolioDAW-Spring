package ifpb.edu.br.portfolio.service;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileDAO profileRepository;

    public List<Profile> getAllProfiles() throws PersistenciaDawException {
        return profileRepository.getAll();
    }

    public Optional<Profile> getProfileById(Long id) throws PersistenciaDawException {
        return Optional.ofNullable(profileRepository.getByID(id));
    }

    public Project saveProfile(Profile profile) throws PersistenciaDawException {
        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id) throws PersistenciaDawException {
        profileRepository.delete(id);
    }
}