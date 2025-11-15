package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.UserDAOImpl;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import java.util.List;

public class MainUserDeleteAll {
    public static void main(String[] args) {
        UserDAOImpl dao = new UserDAOImpl();

        try {
            List<User> users = dao.getAll();
            if (users.isEmpty()) {
                System.out.println("Nenhum usuário encontrado para remoção.");
                return;
            }

            System.out.println("Removendo " + users.size() + " usuários...");

            for (User user : users) {
                dao.delete(user.getId());
                System.out.println("  - Removido usuário ID: " + user.getId() + " (e todos os seus dados em cascata)");
            }

            System.out.println("✅ REMOÇÃO DE TODOS OS USUÁRIOS CONCLUÍDA.");

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA: " + e.getMessage());
        }
    }
}