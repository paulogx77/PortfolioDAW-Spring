package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ProfileDAOImpl extends AbstractDAOImpl<Profile, Long> implements ProfileDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileDAOImpl(JdbcTemplate jdbcTemplate) {
        super(Profile.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> buscarProximos(double lat, double lon, double raioKm) {
        // Chama a função PL/pgSQL 'buscar_usuarios_proximos' criada no banco
        String sql = "SELECT * FROM buscar_usuarios_proximos(?, ?, ?)";

        // O JdbcTemplate mapeia automaticamente o retorno da função (tabela) para List<Map>
        return jdbcTemplate.queryForList(sql, lat, lon, raioKm);
    }
}