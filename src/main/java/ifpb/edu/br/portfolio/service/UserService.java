package ifpb.edu.br.portfolio.service;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // Import correto para o seu modelo User
import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findById(Long id) {
        try {
            return userDAO.getByID(id);
        } catch (PersistenciaDawException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAll() throws PersistenciaDawException {
        return userDAO.getAll();
    }

    public User save(User user) throws PersistenciaDawException {

        // Verifica se é um novo usuário para setar a data
        if (user.getId() == null) {
            // Usa LocalDateTime conforme sua classe User
            user.setDataCriacao(LocalDateTime.now());
        }

        // Verifica se já existe e-mail (regra de negócio)
        User existente = userDAO.findByEmail(user.getEmail());

        if (existente != null) {
            // Se for um novo cadastro (ID nulo) e achou e-mail -> ERRO
            if (user.getId() == null) {
                throw new IllegalStateException("O e-mail " + user.getEmail() + " já está cadastrado.");
            }

            // Se for atualização, mas o e-mail pertence a OUTRO usuário (IDs diferentes) -> ERRO
            if (!existente.getId().equals(user.getId())) {
                throw new IllegalStateException("O e-mail " + user.getEmail() + " já está em uso por outra conta.");
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