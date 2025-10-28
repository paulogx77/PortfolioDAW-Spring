package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.User;
import java.util.List;
import java.util.Optional;

// 1. Interface pura, sem herança do Spring Data JPA
public interface UserDAO {

    // Contrato para Salvar/Atualizar
    User save(User user);

    // Contrato para Buscar por ID (usando Optional para ser robusto)
    Optional<User> findById(Long id);

    // Contrato para Buscar todos
    List<User> findAll();

    // Contrato para Deletar
    void delete(Long id);

    // Adicione outros métodos de busca que você precisar (ex: findByEmail)
    Optional<User> findByEmail(String email);
}