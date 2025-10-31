package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.User;

public interface UserDAO extends DAO<User, Long> {
    User findByEmail(String email) throws PersistenciaDawException;
}