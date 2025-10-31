package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


// 1. Anotação para que o Spring gerencie este componente (é o nosso DAO)
public class UserDAOImpl extends AbstractDAOImpl<User, Long> implements UserDAO {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("portfolio");

    // Construtor obrigatório para a classe base
    public UserDAOImpl() {
        super(User.class, EMF);
    }
}