package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.UserDAOImpl;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;

public class MainUserSave {
    public static void main(String[] args) {
        UserDAOImpl dao = new UserDAOImpl();
        User user = new User();

        String uniqueEmail = "usuario." + System.nanoTime() + "@ifpb.edu.br";

        user.setEmail(uniqueEmail);
        user.setSenha("senha123");

        try {
            dao.save(user); // O método save é herdado do AbstractDAOImpl
            System.out.println("✅ USUÁRIO SALVO COM SUCESSO.");
            System.out.println("ID: " + user.getId() + " | Email: " + user.getEmail());
        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA ao salvar usuário: " + e.getMessage());
        }
    }
}