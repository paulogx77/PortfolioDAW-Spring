package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.impl.UserDAOImpl;
import ifpb.edu.br.portfolio.dao.impl.ProfileDAOImpl;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.model.Profile;

public class MainUserSave {
    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();
        ProfileDAOImpl profileDAO = new ProfileDAOImpl(); // Precisamos dele também

        try {
            // 1. Criar o User
            User user = new User();
            String uniqueEmail = "usuario.completo." + System.nanoTime() + "@ifpb.edu.br";
            user.setEmail(uniqueEmail);
            user.setSenha("senha123");

            // 2. Criar o Profile
            Profile profile = new Profile();
            profile.setNomeCompleto("Usuário Teste Completo");
            profile.setCargo("Desenvolvedor");
            profile.setBio("Bio do usuário de teste.");

            // 3. CONECTAR OS OBJETOS (REGRA 4)
            // Define o 'user' no 'profile' (lado 'dono' do relacionamento)
            profile.setUser(user);

            // Opcional, mas bom para consistência: define o 'profile' no 'user'
            user.setProfile(profile);

            // 4. SALVAR
            // IMPORTANTE: Salve o User primeiro, pois o Profile depende dele
            // Devido ao CascadeType.ALL no User, salvar o User também salvará o Profile.
            userDAO.save(user);

            System.out.println("✅ USUÁRIO E PERFIL SALVOS COM SUCESSO (via Cascade).");
            System.out.println("User ID: " + user.getId() + " | Profile ID: " + profile.getId());

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA: " + e.getMessage());
            e.printStackTrace();
        }
    }
}