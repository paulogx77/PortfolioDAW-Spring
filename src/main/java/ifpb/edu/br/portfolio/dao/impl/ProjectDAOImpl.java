package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.DAO;
import ifpb.edu.br.portfolio.dao.ProjectDAO;
import ifpb.edu.br.portfolio.model.Project;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ProjectDAOImpl extends AbstractDAOImpl<Project, Long> implements ProjectDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("portfolio");

    public ProjectDAOImpl() {
        super(Project.class, EMF);
    }
}
