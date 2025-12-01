package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.ProjectDAO;
import ifpb.edu.br.portfolio.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
// Project substitui E, Long substitui K
public class ProjectDAOImpl extends AbstractDAOImpl<Project, Long> implements ProjectDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectDAOImpl(JdbcTemplate jdbcTemplate) {
        super(Project.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    // NÃO sobrescreva o método save aqui manualmente a menos que seja necessário.
    // Se você tinha um "public Project save(Project p)" aqui dentro que conflitava, remova-o.
    // O save virá herdado do AbstractDAOImpl.
}