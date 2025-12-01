package ifpb.edu.br.portfolio.controller;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public List<Profile> getAllProfiles() throws PersistenciaDawException {
        // Lembre-se: No Service o nome provavelmente é 'findAll'
        return profileService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        try {
            // --- CORREÇÃO AQUI ---
            // De: profileService.getProfileById(id);
            // Para: profileService.findById(id);
            Profile profile = profileService.findById(id);

            if (profile != null) {
                return ResponseEntity.ok(profile);
            }
            return ResponseEntity.notFound().build();

        } catch (PersistenciaDawException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public Profile createProfile(@RequestBody Profile profile) throws PersistenciaDawException {
        return profileService.save(profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        try {
            profileService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (PersistenciaDawException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}