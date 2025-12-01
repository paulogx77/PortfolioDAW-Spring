package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication; // Importe sua classe principal do Spring aqui!
import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.dao.ProjectDAO;
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

public class MainProjectSave {

    public static void main(String[] args) {
        // 1. INICIALIZA O SPRING MANUALMENTE
        // (Substitua 'PortfolioApplication.class' pelo nome da classe principal do seu projeto)
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. RECUPERA OS DAOS JÁ PRONTOS (COM JDBC E JPA INJETADOS)
            UserDAO userDAO = context.getBean(UserDAO.class);
            ProfileDAO profileDAO = context.getBean(ProfileDAO.class);
            ProjectDAO projectDAO = context.getBean(ProjectDAO.class);

            System.out.println("--- INICIANDO TESTE ISOLADO ---");

            // 3. EXECUTA SUA LÓGICA NORMALMENTE
            User user = new User();
            user.setEmail("teste.main." + System.nanoTime() + "@ifpb.edu.br");
            user.setSenha("123");
            user.setDataCriacao(LocalDateTime.now());

            userDAO.save(user);
            System.out.println("✅ User salvo: " + user.getId());

            Profile profile = new Profile();
            profile.setNome("Perfil Main");
            profile.setUser(user);

            profileDAO.save(profile);
            System.out.println("✅ Profile salvo: " + profile.getId());

            Project project = new Project();
            project.setTitulo("Projeto via Main");
            project.setProfile(profile);

            projectDAO.save(project);
            System.out.println("✅ Project salvo: " + project.getId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4. ENCERRA O SPRING AO TERMINAR
            context.close();
        }
    }
}