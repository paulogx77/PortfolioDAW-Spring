package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.ProjectDAOImpl;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import java.util.List;

public class MainProjectDeleteAll {
    public static void main(String[] args) {
        ProjectDAOImpl dao = new ProjectDAOImpl();

        try {
            List<Project> projects = dao.getAll(); // Método herdado

            if (projects.isEmpty()) {
                System.out.println("Nenhum projeto encontrado para remoção.");
                return;
            }

            System.out.println("Removendo " + projects.size() + " projetos...");

            for (Project project : projects) {
                dao.delete(project.getId());
                System.out.println("  - Removido projeto ID: " + project.getId());
            }

            System.out.println("✅ REMOÇÃO DE TODOS OS PROJETOS CONCLUÍDA.");

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA ao remover projetos: " + e.getMessage());
        }
    }
}