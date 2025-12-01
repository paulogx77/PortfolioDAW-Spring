package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication; // Importe a classe principal do seu projeto!
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

public class MainUserSave {

    public static void main(String[] args) {
        // 1. INICIALIZA O SPRING MANUALMENTE
        // Isso carrega o banco de dados, JPA, JDBC e cria os DAOs.
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. RECUPERA O DAO GERENCIADO PELO SPRING
            // Não usamos 'new UserDAOImpl()', pedimos ao Spring o que ele criou.
            UserDAO userDAO = context.getBean(UserDAO.class);

            System.out.println("--- INICIANDO TESTE ISOLADO DE USUÁRIO ---");

            // 3. CRIAR O USER
            User user = new User();
            String uniqueEmail = "usuario.completo." + System.nanoTime() + "@ifpb.edu.br";
            user.setEmail(uniqueEmail);
            user.setSenha("senha123");
            user.setDataCriacao(LocalDateTime.now()); // Ajustado para o modelo atual

            // 4. CRIAR O PROFILE
            Profile profile = new Profile();
            // Ajustei os nomes dos métodos para bater com a classe Profile.java que fizemos
            profile.setNome("Usuário Teste Completo");
            profile.setCargo("Desenvolvedor Fullstack");
            profile.setBiografia("Bio do usuário de teste.");
            profile.setImagemUrl("http://exemplo.com/avatar.png");

            // 5. CONECTAR OS OBJETOS (Bidirecional)
            profile.setUser(user); // Essencial (Dono da chave estrangeira)
            user.setProfile(profile); // Opcional, mas boa prática para manter consistência em memória

            // 6. SALVAR
            // Graças ao CascadeType.ALL configurado no User.java,
            // salvar o User vai salvar o Profile automaticamente.
            userDAO.save(user);

            System.out.println("✅ USUÁRIO E PERFIL SALVOS COM SUCESSO (via Cascade).");
            System.out.println("User ID: " + user.getId());

            // Nota: O ID do profile pode não estar populado no objeto Java imediatamente após o save
            // dependendo da implementação do Hibernate, mas no banco estará lá.
            if(user.getProfile() != null) {
                System.out.println("Profile associado: " + user.getProfile().getNome());
            }

        } catch (Exception e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA:");
            e.printStackTrace();
        } finally {
            // 7. ENCERRA O SPRING
            context.close();
        }
    }
}