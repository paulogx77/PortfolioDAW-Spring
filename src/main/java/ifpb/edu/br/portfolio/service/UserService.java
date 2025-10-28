package ifpb.edu.br.portfolio.service;

import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// A Service é o ponto de entrada da lógica de negócios
@Service
public class UserService {

    // A camada Service só lida com a Interface DAO (baixo acoplamento)
    private final UserDAO userDAO;

    // Injeção de Dependência (o Spring injetará a implementação UserDAOImpl)
    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Busca um User pelo ID.
     * @param id O ID do usuário.
     * @return O objeto User, se encontrado.
     */
    public Optional<User> findById(Long id) {
        // Nenhuma lógica de negócio complexa aqui, apenas delegação para o DAO
        return userDAO.findById(id);
    }

    /**
     * Busca todos os usuários.
     * @return Uma lista de todos os usuários.
     */
    public List<User> findAll() {
        return userDAO.findAll();
    }

    /**
     * Cria ou atualiza um usuário.
     * Inclui lógica de negócio: garantir que o e-mail não seja duplicado.
     * @param user O objeto User a ser salvo.
     * @return O User salvo/atualizado.
     */
    public User save(User user) {

        // 1. LÓGICA DE NEGÓCIO: Define a data de criação apenas se for uma nova criação
        if (user.getId() == null) {
            user.setDataCriacao(java.time.LocalDateTime.now());
        }

        // 2. LÓGICA DE NEGÓCIO: Validação de e-mail (Checagem de unicidade)

        Optional<User> userWithSameEmail = userDAO.findByEmail(user.getEmail());

        if (userWithSameEmail.isPresent()) {

            // Se o usuário passado (o que está sendo salvo) for NOVO (ID null),
            // é um erro de duplicidade.
            if (user.getId() == null) {
                throw new IllegalStateException("O e-mail " + user.getEmail() + " já está cadastrado.");
            }

            // CORREÇÃO CRÍTICA AQUI:
            // Se o e-mail encontrado (userWithSameEmail) NÃO tem o mesmo ID
            // do usuário que estamos salvando (user), é uma tentativa de roubo de e-mail.
            if (!userWithSameEmail.get().getId().equals(user.getId())) {
                throw new IllegalStateException("O e-mail " + user.getEmail() + " já está cadastrado em outra conta.");
            }
        }

        // 3. Delega a persistência para o DAO
        return userDAO.save(user);
    }

    /**
     * Deleta um usuário.
     * @param id O ID do usuário a ser deletado.
     */
    public void delete(Long id) {
        // LÓGICA DE NEGÓCIO: Poderia checar permissões ou dependências aqui.
        if (userDAO.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado para exclusão.");
        }
        userDAO.delete(id);
    }
}