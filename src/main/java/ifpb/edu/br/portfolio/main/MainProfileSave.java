package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication; // Importe a classe principal do projeto
import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

public class MainProfileSave {

    public static void main(String[] args) {
        // 1. INICIALIZA O SPRING MANUALMENTE
        // Carrega JPA, JDBC e configurações do banco
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. RECUPERA OS DAOS (BEANS) DO SPRING
            // Não usamos 'new', pedimos a instância pronta ao Spring
            UserDAO userDAO = context.getBean(UserDAO.class);
            ProfileDAO profileDAO = context.getBean(ProfileDAO.class);

            System.out.println("--- INICIANDO TESTE: SALVAR PERFIL ---");

            // 3. CRIAR E SALVAR O USER (Dono do perfil) PRIMEIRO
            User user = new User();
            user.setEmail("usuario.perfil." + System.nanoTime() + "@ifpb.edu.br");
            user.setSenha("123");
            user.setDataCriacao(LocalDateTime.now()); // Necessário conforme modelo atual

            userDAO.save(user);
            System.out.println("✅ Usuário salvo (ID: " + user.getId() + ")");

            // 4. CRIAR O PROFILE
            Profile profile = new Profile();

            // Correção dos nomes dos métodos conforme sua classe Profile.java:
            profile.setNome("Usuário com Perfil Separado"); // Antes era setNomeCompleto
            profile.setCargo("Analista de Testes");
            profile.setBiografia("Bio de teste para salvar perfil."); // Antes era setBio
            // profile.setImagemUrl("..."); // Opcional

            // 5. CONECTAR OS OBJETOS (REGRA 4)
            profile.setUser(user); // O Perfil precisa saber quem é o dono

            // 6. SALVAR O PROFILE
            profileDAO.save(profile);

            System.out.println("✅ PERFIL SALVO COM SUCESSO.");
            System.out.println("Perfil ID: " + profile.getId() + " | Pertence ao User ID: " + user.getId());

        } catch (Exception e) {
            System.err.println("❌ ERRO NO TESTE:");
            e.printStackTrace();
        } finally {
            // 7. FECHA O SPRING
            context.close();
        }
    }
}