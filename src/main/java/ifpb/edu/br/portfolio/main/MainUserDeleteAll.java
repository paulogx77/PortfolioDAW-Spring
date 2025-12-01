package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication; // Importe a classe principal do seu projeto
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class MainUserDeleteAll {

    public static void main(String[] args) {
        // 1. INICIALIZA O SPRING MANUALMENTE
        // Isso sobe o pool de conexões e configura o JPA/JDBC
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. RECUPERA O DAO DO SPRING
            // Substitui o 'new UserDAOImpl()' que daria erro
            UserDAO dao = context.getBean(UserDAO.class);

            System.out.println("--- INICIANDO REMOÇÃO TOTAL DE USUÁRIOS ---");

            // 3. BUSCA TODOS OS USUÁRIOS
            List<User> users = dao.getAll();

            if (users.isEmpty()) {
                System.out.println("⚠️ Nenhum usuário encontrado para remoção.");
            } else {
                System.out.println("Encontrados " + users.size() + " usuários. Iniciando exclusão...");

                // 4. DELETA UM POR UM
                for (User user : users) {
                    // Devido ao CascadeType.ALL configurado na classe User,
                    // isso também vai apagar o Perfil e os Comentários desse usuário.
                    dao.delete(user.getId());

                    System.out.println("🗑️ Removido usuário ID: " + user.getId() +
                            " (" + user.getEmail() + ") e seus dados em cascata.");
                }

                System.out.println("✅ REMOÇÃO DE TODOS OS USUÁRIOS CONCLUÍDA.");
            }

        } catch (Exception e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA:");
            e.printStackTrace();
        } finally {
            // 5. ENCERRA O SPRING E LIBERA O BANCO
            context.close();
        }
    }
}