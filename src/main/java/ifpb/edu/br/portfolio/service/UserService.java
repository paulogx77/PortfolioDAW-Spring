package ifpb.edu.br.portfolio.service;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findById(Long id) { // Retorna User, não Optional<User>
        try {
            return userDAO.getByID(id); // Chama getByID, não findById
        } catch (PersistenciaDawException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAll() throws PersistenciaDawException {
        return userDAO.getAll();
    }

    public Project save(User user) throws PersistenciaDawException {

        if (user.getId() == null) {
            user.setDataCriacao(java.time.LocalDateTime.now());
        }


        Optional<User> userWithSameEmail = Optional.ofNullable(userDAO.findByEmail(user.getEmail()));

        if (userWithSameEmail.isPresent()) {

            if (user.getId() == null) {
                throw new IllegalStateException("O e-mail " + user.getEmail() + " já está cadastrado.");
            }

            if (!userWithSameEmail.get().getId().equals(user.getId())) {
                throw new IllegalStateException("O e-mail " + user.getEmail() + " já está cadastrado em outra conta.");
            }
        }

        return userDAO.save(user);
    }

    public void delete(Long id) throws PersistenciaDawException {

        if (userDAO.getByID(id) == null) {
            throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado para exclusão.");
        }

        userDAO.delete(id);
    }
}