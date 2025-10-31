package ifpb.edu.br.portfolio.service;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.dao.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectDAO projectRepository;

    public List<Project> getAllProjects() throws PersistenciaDawException {
        return projectRepository.getAll();
    }

    public Optional<Project> getProjectById(Long id) throws PersistenciaDawException {
        return Optional.ofNullable(projectRepository.getByID(id));
    }

    public Project saveProject(Project project) throws PersistenciaDawException {
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        try {
            projectRepository.delete(id);
        } catch (PersistenciaDawException e) {
            e.printStackTrace();
        }
    }
}
