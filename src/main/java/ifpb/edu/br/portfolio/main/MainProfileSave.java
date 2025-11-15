package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.impl.UserDAOImpl;
import ifpb.edu.br.portfolio.dao.impl.ProfileDAOImpl;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.model.Profile;

public class MainProfileSave {
    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();
        ProfileDAOImpl profileDAO = new ProfileDAOImpl();

        try {
            // 1. Criar e salvar um User (dono do perfil) primeiro
            User user = new User();
            user.setEmail("usuario.perfil." + System.nanoTime() + "@ifpb.edu.br");
            user.setSenha("123");
            userDAO.save(user);
            System.out.println("Usuário salvo (ID: " + user.getId() + ")");

            // 2. Criar o Profile
            Profile profile = new Profile();
            profile.setNomeCompleto("Usuário com Perfil Separado");
            profile.setCargo("Analista de Testes");
            profile.setBio("Bio de teste para salvar perfil.");

            // 3. CONECTAR OS OBJETOS (REGRA 4)
            profile.setUser(user);

            // 4. Salvar o Profile
            profileDAO.save(profile);

            System.out.println("✅ PERFIL SALVO COM SUCESSO.");
            System.out.println("Perfil ID: " + profile.getId() + " | User ID: " + user.getId());

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA: " + e.getMessage());
            e.printStackTrace();
        }
    }
}