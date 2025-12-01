package ifpb.edu.br.portfolio.dao;

import java.util.List;

// E = Entidade (ex: Project), K = Chave (ex: Long)
public interface DAO<E, K> {
    E save(E obj) throws PersistenciaDawException;
    E update(E obj) throws PersistenciaDawException;
    void delete(K id) throws PersistenciaDawException;
    E getByID(K id) throws PersistenciaDawException;
    List<E> getAll() throws PersistenciaDawException;
}