package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.UserDAOImpl;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import java.util.List;

public class MainUserDeleteAll {
    public static void main(String[] args) {
        UserDAOImpl dao = new UserDAOImpl();

        try {
            List<User> users = dao.getAll(); // Método herdado

            if (users.isEmpty()) {
                System.out.println("Nenhum usuário encontrado para remoção.");
                return;
            }

            System.out.println("Removendo " + users.size() + " usuários...");

            for (User user : users) {
                dao.delete(user.getId()); // O método delete usa a Chave Primária (ID)
                System.out.println("  - Removido usuário ID: " + user.getId());
            }

            System.out.println("✅ REMOÇÃO DE TODOS OS USUÁRIOS CONCLUÍDA.");

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA ao remover usuários: " + e.getMessage());
        }
    }
}