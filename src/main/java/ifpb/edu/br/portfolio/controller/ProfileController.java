package ifpb.edu.br.portfolio.controller;

import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
@CrossOrigin(origins = "http://localhost:3000") // Permite acesso do nosso front-end React
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id)
                .map(ResponseEntity::ok) // Se encontrar, retorna 200 OK com o perfil
                .orElse(ResponseEntity.notFound().build()); // Se não, retorna 404 Not Found
    }

    @PostMapping
    public Profile createProfile(@RequestBody Profile profile) {
        // A lógica para associar a um User viria aqui
        return profileService.saveProfile(profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}