package ifpb.edu.br.portfolio.controller;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public List<Profile> getAllProfiles() throws PersistenciaDawException {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) throws PersistenciaDawException {
        return profileService.getProfileById(id)
                .map(ResponseEntity::ok) // Se encontrar, retorna 200 OK com o perfil
                .orElse(ResponseEntity.notFound().build()); // Se não, retorna 404 Not Found
    }

    @PostMapping
    public Project createProfile(@RequestBody Profile profile) throws PersistenciaDawException {
        // A lógica para associar a um User viria aqui
        return profileService.saveProfile(profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) throws PersistenciaDawException {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}