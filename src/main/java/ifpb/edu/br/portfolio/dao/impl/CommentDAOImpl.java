package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.CommentDAO;
import ifpb.edu.br.portfolio.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CommentDAOImpl extends AbstractDAOImpl<Comment, Long> implements CommentDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentDAOImpl(JdbcTemplate jdbcTemplate) {
        super(Comment.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    // Se quiser contar comentários de um projeto via SQL (Muito mais rápido que JPA)
    public int contarComentariosPorProjeto(Long projetoId) {
        // Tabela: comentario | Coluna FK: projeto_id
        String sql = "SELECT count(*) FROM comentario WHERE projeto_id = ?";

        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, projetoId);
        return total != null ? total : 0;
    }
}