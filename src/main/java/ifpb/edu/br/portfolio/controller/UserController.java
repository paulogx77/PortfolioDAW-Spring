package ifpb.edu.br.portfolio.controller;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.model.Project;
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

    @GetMapping
    public List<User> findAllUsers() throws PersistenciaDawException {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createUser(@RequestBody User user) throws PersistenciaDawException {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateUser(@PathVariable Long id, @RequestBody User userDetails) throws PersistenciaDawException {
        User existingUser = userService.findById(id);

        if (existingUser != null) {
            userDetails.setId(id);

            Project updatedUser = userService.save(userDetails);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) throws PersistenciaDawException {
        userService.delete(id);
    }
}