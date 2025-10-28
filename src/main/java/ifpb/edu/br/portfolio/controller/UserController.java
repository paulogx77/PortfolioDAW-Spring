package ifpb.edu.br.portfolio.controller;

import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Corrigido: De getAllUsers() para findAll()
    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    // Corrigido: De getUserById(id) para findById(id)
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        // Usa o método findById que retorna Optional
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Corrigido: De saveUser(user) para save(user)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        // A lógica de negócio está no service (email único, data de criação)
        return userService.save(user);
    }

    // Corrigido: De saveUser(user) para save(user) - usado também para UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        // Verifica se o usuário existe antes de tentar atualizar
        return userService.findById(id)
                .map(existingUser -> {
                    // Define o ID no objeto recebido, garantindo que o save() faça um update
                    userDetails.setId(id);
                    // Lógica de update no Service
                    return ResponseEntity.ok(userService.save(userDetails));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Corrigido: De deleteUser(id) para delete(id)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}