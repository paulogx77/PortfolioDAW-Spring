package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication; // Sua classe principal
import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.model.Profile;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class MainProfileDeleteAll {

    public static void main(String[] args) {
        // 1. Acorda o Spring
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. Pede o DAO para o Spring
            ProfileDAO profileDAO = context.getBean(ProfileDAO.class);

            // 3. Lógica
            List<Profile> profiles = profileDAO.getAll();

            if (profiles.isEmpty()) {
                System.out.println("Nenhum perfil para deletar.");
            } else {
                for (Profile p : profiles) {
                    profileDAO.delete(p.getId());
                    System.out.println("🗑️ Deletado perfil ID: " + p.getId());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4. Dorme o Spring
            context.close();
        }
    }
}