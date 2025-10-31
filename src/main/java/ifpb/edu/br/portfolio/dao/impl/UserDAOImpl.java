package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import jakarta.persistence.*;


public class UserDAOImpl extends AbstractDAOImpl<User, Long> implements UserDAO {
    public UserDAOImpl() {
        super(User.class, EMF);
    }

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("portfolio");

    @Override
    public User findByEmail(String email) throws PersistenciaDawException {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            throw new PersistenciaDawException("Erro ao buscar usuário por e-mail.", e);
        }
    }
}