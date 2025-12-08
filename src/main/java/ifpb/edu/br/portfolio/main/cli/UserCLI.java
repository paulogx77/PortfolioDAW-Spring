package ifpb.edu.br.portfolio.main.cli;

import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Component
public class UserCLI {

    @Autowired
    private UserDAO userDAO;

    public void cadastrarUsuario(Scanner scanner) {
        try {
            System.out.println("\n--- NOVO USUÁRIO ---");
            System.out.print("E-mail: ");
            String email = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            User user = new User();
            user.setEmail(email);
            user.setSenha(senha);
            user.setDataCriacao(LocalDateTime.now());

            userDAO.save(user);
            System.out.println("✅ Usuário salvo! ID: " + user.getId());
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    public void listarUsuarios() {
        try {
            List<User> users = userDAO.getAll();
            System.out.println("\n--- USUÁRIOS CADASTRADOS (JPA) ---");
            for (User u : users) {
                String perfilInfo = (u.getProfile() != null) ? "Com Perfil (ID " + u.getProfile().getId() + ")" : "Sem Perfil";
                System.out.printf("ID: %d | Email: %s | %s%n", u.getId(), u.getEmail(), perfilInfo);
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao listar: " + e.getMessage());
        }
    }

    public void deletarUsuario(Scanner scanner) {
        try {
            System.out.print("Digite o ID do Usuário para deletar: ");
            Long id = Long.parseLong(scanner.nextLine());
            userDAO.delete(id);
            System.out.println("✅ Usuário e dados vinculados deletados.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao deletar: " + e.getMessage());
        }
    }
}