package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map; // <--- Importante

@Repository
@Transactional
public class UserDAOImpl extends AbstractDAOImpl<User, Long> implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        super(User.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByEmail(String email) throws PersistenciaDawException {
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            throw new PersistenciaDawException("Erro ao buscar usuário.", e);
        }
    }

    // --- AQUI ESTÁ O SEU SQL DIRETO NA CLASSE ---
    @Override
    public List<Map<String, Object>> buscarRelatorioGeral() {
        // SQL: Seleciona Email, Nome do Perfil e Conta quantos projetos o usuário tem.
        String sql = """
            SELECT 
                u.email AS usuario_email, 
                pf.nome AS nome_perfil, 
                COUNT(pj.id) AS qtd_projetos
            FROM usuario u
            LEFT JOIN perfil pf ON pf.usuario_id = u.id
            LEFT JOIN projeto pj ON pj.perfil_id = pf.id
            GROUP BY u.email, pf.nome
            ORDER BY qtd_projetos DESC
        """;

        // O queryForList retorna uma lista de mapas: [{usuario_email=..., qtd_projetos=...}, {...}]
        return jdbcTemplate.queryForList(sql);
    }
}