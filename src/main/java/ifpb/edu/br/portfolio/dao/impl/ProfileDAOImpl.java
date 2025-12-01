package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProfileDAOImpl extends AbstractDAOImpl<Profile, Long> implements ProfileDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileDAOImpl(JdbcTemplate jdbcTemplate) {
        super(Profile.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    // Exemplo: Se você precisar buscar o ID do perfil via SQL nativo (JDBC)
    // Cuidado com o nome da coluna 'usuario_id' do mapa conceitual
    /*
    public Long buscarIdDoPerfilPorUsuario(Long usuarioId) {
        String sql = "SELECT id FROM perfil WHERE usuario_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, usuarioId);
    }
    */
}