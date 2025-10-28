package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.stereotype.Repository; // Usa o @Repository para gerenciar
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

// 1. Anotação para que o Spring gerencie este componente (é o nosso DAO)
@Repository
public class UserDAOImpl implements UserDAO {

    // 2. Injeta o gerenciador de entidades do JPA (preciso para persistir/buscar)
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional // 3. Garante que esta operação ocorra dentro de uma transação de banco
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user); // Insere novo
        } else {
            user = entityManager.merge(user); // Atualiza existente
        }
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        // Usa o método find do EntityManager
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        // Usa JPQL para buscar todos
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id).ifPresent(user -> entityManager.remove(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        // Exemplo de Query customizada
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();
    }
}