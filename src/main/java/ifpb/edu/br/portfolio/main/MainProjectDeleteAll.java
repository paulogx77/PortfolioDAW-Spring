package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication; // Importe a classe principal do seu projeto!
import ifpb.edu.br.portfolio.dao.ProjectDAO;
import ifpb.edu.br.portfolio.model.Project;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class MainProjectDeleteAll {

    public static void main(String[] args) {
        // 1. INICIALIZA O SPRING MANUALMENTE
        // Isso carrega as configurações do application.properties e conecta no banco
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. RECUPERA O DAO CONFIGURADO PELO SPRING
            // Substitui 'new ProjectDAOImpl()' pela injeção de dependência real
            ProjectDAO dao = context.getBean(ProjectDAO.class);

            System.out.println("--- INICIANDO REMOÇÃO TOTAL DE PROJETOS ---");

            // 3. BUSCAR TODOS
            List<Project> projects = dao.getAll();

            if (projects.isEmpty()) {
                System.out.println("⚠️ Nenhum projeto encontrado para remoção.");
            } else {
                System.out.println("Encontrados " + projects.size() + " projetos. Iniciando exclusão...");

                // 4. DELETAR UM POR UM
                for (Project project : projects) {
                    // Devido ao CascadeType.REMOVE configurado na classe Project (para Comentários),
                    // os comentários vinculados serão apagados automaticamente.
                    dao.delete(project.getId());

                    System.out.println("🗑️ Removido projeto ID: " + project.getId() +
                            " (Título: " + project.getTitulo() + ")");
                }

                System.out.println("✅ REMOÇÃO DE TODOS OS PROJETOS CONCLUÍDA.");
            }

        } catch (Exception e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA:");
            e.printStackTrace();
        } finally {
            // 5. ENCERRA O SPRING
            context.close();
        }
    }
}