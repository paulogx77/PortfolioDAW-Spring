package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.User;

// 1. Interface pura, sem herança do Spring Data JPA
public interface UserDAO extends DAO<User, Long> {

}