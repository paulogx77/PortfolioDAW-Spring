package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.main.cli.*;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class SistemaPortfolioCLI implements CommandLineRunner {

    @Autowired private AuthCLI authCLI;
    @Autowired private UserCLI userCLI;
    @Autowired private ProfileCLI profileCLI;
    @Autowired private ProjectCLI projectCLI;
    @Autowired private ReportCLI reportCLI;

    // Essa variável guarda a sessão do usuário
    private User currentUser = null;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean sistemaRodando = true;

        System.out.println("🚀 SISTEMA INICIADO");

        while (sistemaRodando) {
            if (currentUser == null) {
                // --- MODO VISITANTE ---
                exibirMenuVisitante();
                int opcao = lerOpcao(scanner);
                switch (opcao) {
                    case 1 -> currentUser = authCLI.realizarLogin(scanner);
                    case 2 -> userCLI.cadastrarUsuario(scanner); // Cadastro não precisa de login
                    case 3 -> userCLI.listarUsuarios(); // Listagem pública
                    case 0 -> sistemaRodando = false;
                    default -> System.out.println("Opção inválida.");
                }
            } else {
                // --- MODO LOGADO ---
                System.out.println("\n👤 Olá, " + currentUser.getEmail());
                exibirMenuLogado();
                int opcao = lerOpcao(scanner);

                switch (opcao) {
                    case 1 -> profileCLI.cadastrarPerfil(scanner, currentUser);
                    case 2 -> projectCLI.cadastrarProjeto(scanner, currentUser);
                    case 3 -> projectCLI.comentarProjeto(scanner, currentUser); // Comentar pede o email do autor ou usa o logado? (Ideal usar logado)
                    case 4 -> reportCLI.gerarRelatorioGeral();
                    case 5 -> {
                        System.out.println("Saindo da conta...");
                        currentUser = null; // LOGOUT
                    }
                    case 0 -> sistemaRodando = false;
                    default -> System.out.println("Opção inválida.");
                }
            }
        }
        System.out.println("Sistema encerrado.");
    }

    private void exibirMenuVisitante() {
        System.out.println("\n=== BEM-VINDO ===");
        System.out.println("1. Entrar (Login)");
        System.out.println("2. Criar Nova Conta");
        System.out.println("3. Listar Usuários da Plataforma");
        System.out.println("0. Sair");
        System.out.print(">>> ");
    }

    private void exibirMenuLogado() {
        System.out.println("\n=== PAINEL DO USUÁRIO ===");
        System.out.println("1. Meu Perfil (Criar/Editar)");
        System.out.println("2. Novo Projeto");
        System.out.println("3. Comentar em Projetos");
        System.out.println("4. Relatório Geral (JDBC)");
        System.out.println("5. Logout (Sair da conta)");
        System.out.println("0. Fechar Sistema");
        System.out.print(">>> ");
    }

    private int lerOpcao(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}