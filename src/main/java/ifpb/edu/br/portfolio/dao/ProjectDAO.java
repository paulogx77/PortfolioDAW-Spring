package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDAO extends JpaRepository<Project, Long> {
}
