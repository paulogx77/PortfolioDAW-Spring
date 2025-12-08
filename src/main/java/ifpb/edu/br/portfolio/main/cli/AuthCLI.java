package ifpb.edu.br.portfolio.main.cli;

import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AuthCLI {

    @Autowired
    private UserDAO userDAO;

    public User realizarLogin(Scanner scanner) {
        System.out.println("\n=== LOGIN ===");
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            // Busca no banco
            User user = userDAO.findByEmail(email);

            if (user != null && user.getSenha().equals(senha)) {
                System.out.println("✅ Login realizado com sucesso! Bem-vindo(a).");
                return user; // Retorna o objeto do usuário para a sessão
            } else {
                System.out.println("❌ E-mail ou senha inválidos.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar logar: " + e.getMessage());
        }
        return null; // Login falhou
    }
}