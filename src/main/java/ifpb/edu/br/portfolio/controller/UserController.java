package ifpb.edu.br.portfolio.controller;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Ajuste a rota conforme seu gosto (ex: /api/v1/users)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() throws PersistenciaDawException {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    // --- AQUI ESTAVA O ERRO ---
    // Antes devia estar: public Project createUser(...) ou ResponseEntity<Project>
    // Agora corrigido para: User
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User novoUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUser);
        } catch (PersistenciaDawException | IllegalStateException e) {
            // Retorna erro 400 (Bad Request) se der erro de validação (ex: email duplicado)
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (PersistenciaDawException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}