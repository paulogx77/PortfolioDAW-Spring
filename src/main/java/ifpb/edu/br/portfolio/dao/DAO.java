package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.Project;

import java.util.List;

public interface DAO<E, T>{
    Project save(E obj) throws PersistenciaDawException;

    E update(E obj) throws PersistenciaDawException;

    void delete(T primaryKey) throws PersistenciaDawException;

    E getByID(T primaryKey) throws PersistenciaDawException;

    List<E> getAll() throws PersistenciaDawException;
}
