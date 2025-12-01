package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.DAO;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.List;

// E deve bater com E da interface DAO
public abstract class AbstractDAOImpl<E, K> implements DAO<E, K> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> entityClass;

    public AbstractDAOImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public E save(E obj) throws PersistenciaDawException {
        try {
            if (entityManager.contains(obj)) {
                return entityManager.merge(obj);
            } else {
                entityManager.persist(obj);
                return obj; // Retorna o objeto E
            }
        } catch (PersistenceException pe) {
            throw new PersistenciaDawException("Erro ao salvar.", pe);
        }
    }

    @Override
    public E update(E obj) throws PersistenciaDawException {
        try {
            return entityManager.merge(obj);
        } catch (PersistenceException pe) {
            throw new PersistenciaDawException("Erro ao atualizar.", pe);
        }
    }

    @Override
    public void delete(K id) throws PersistenciaDawException {
        try {
            E obj = entityManager.find(entityClass, id);
            if (obj != null) {
                entityManager.remove(obj);
            }
        } catch (PersistenceException pe) {
            throw new PersistenciaDawException("Erro ao deletar.", pe);
        }
    }

    @Override
    public E getByID(K id) throws PersistenciaDawException {
        try {
            return entityManager.find(entityClass, id);
        } catch (PersistenceException pe) {
            throw new PersistenciaDawException("Erro ao buscar ID.", pe);
        }
    }

    @Override
    public List<E> getAll() throws PersistenciaDawException {
        try {
            String jpql = "SELECT obj FROM " + entityClass.getSimpleName() + " obj";
            TypedQuery<E> query = entityManager.createQuery(jpql, entityClass);
            return query.getResultList();
        } catch (PersistenceException pe) {
            throw new PersistenciaDawException("Erro ao listar.", pe);
        }
    }
}