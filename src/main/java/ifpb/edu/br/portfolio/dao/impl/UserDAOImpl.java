package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    // @Cacheable: Antes de executar, olha no Redis.
    // value = nome da "pasta" no Redis.
    // key = qual parâmetro usar como chave única.
    @Cacheable(value = "usersByEmail", key = "#email")
    public User findByEmail(String email) throws PersistenciaDawException {
        // Vou colocar um print para provar quando ele vai no banco de verdade
        System.out.println("🔄 Consultando Banco de Dados (Postgres) para: " + email);

        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            throw new PersistenciaDawException("Erro ao buscar por email.", e);
        }
    }

    // @CacheEvict: Se eu salvar/atualizar alguém, o cache pode ficar velho.
    // allEntries = true: Limpa todo o cache de emails para garantir consistência.
    // (Num sistema gigante faríamos algo mais refinado, mas para começar é o ideal)
    @Override
    @CacheEvict(value = "usersByEmail", allEntries = true)
    public User save(User user) throws PersistenciaDawException {
        return super.save(user);
    }

    // Mesma coisa ao deletar
    @Override
    @CacheEvict(value = "usersByEmail", allEntries = true)
    public void delete(Long id) throws PersistenciaDawException {
        super.delete(id);
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