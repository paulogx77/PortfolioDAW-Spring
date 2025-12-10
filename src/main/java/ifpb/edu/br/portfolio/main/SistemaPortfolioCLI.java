package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.main.cli.*;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class SistemaPortfolioCLI implements CommandLineRunner {

    // --- Injeção dos Módulos CLI (Menus Específicos) ---
    @Autowired private AuthCLI authCLI;
    @Autowired private UserCLI userCLI;
    @Autowired private ProfileCLI profileCLI;
    @Autowired private ProjectCLI projectCLI;
    @Autowired private ReportCLI reportCLI;
    @Autowired private LogCLI logCLI;       // MongoDB (Visualizar Logs)

    // Injetamos o Service de Log para registrar o Logout manualmente aqui
    @Autowired private LogService logService;

    // Variável que guarda a sessão do usuário (null = não logado)
    private User currentUser = null;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean sistemaRodando = true;

        System.out.println("\n=============================================");
        System.out.println("   🚀 SISTEMA PORTFOLIO (FULL STACK CLI) 🚀   ");
        System.out.println("=============================================");
        System.out.println("Tecnologias: Spring Boot | JPA | JDBC | Redis | MinIO | MongoDB");

        while (sistemaRodando) {

            if (currentUser == null) {
                // ------------------------------------------------
                // MODO VISITANTE (Não logado)
                // ------------------------------------------------
                exibirMenuVisitante();
                int opcao = lerOpcao(scanner);

                switch (opcao) {
                    case 1 -> currentUser = authCLI.realizarLogin(scanner);
                    case 2 -> userCLI.cadastrarUsuario(scanner);
                    case 3 -> userCLI.listarUsuarios(); // Listagem pública
                    case 0 -> {
                        System.out.println("Encerrando sistema...");
                        sistemaRodando = false;
                    }
                    default -> System.out.println("❌ Opção inválida.");
                }

            } else {
                // ------------------------------------------------
                // MODO LOGADO (Sessão Ativa)
                // ------------------------------------------------
                System.out.println("\n👤 LOGADO COMO: " + currentUser.getEmail());
                exibirMenuLogado();
                int opcao = lerOpcao(scanner);

                switch (opcao) {
                    case 1 -> profileCLI.cadastrarPerfil(scanner, currentUser);
                    case 2 -> projectCLI.cadastrarProjeto(scanner, currentUser);
                    case 3 -> projectCLI.comentarProjeto(scanner, currentUser);

                    case 4 -> reportCLI.gerarRelatorioGeral(); // JDBC (Postgres)
                    case 5 -> logCLI.exibirLogsAudit();        // MongoDB (Logs)

                    case 6 -> {
                        // Logout
                        logService.registrarLog("LOGOUT", "Usuário encerrou a sessão", currentUser.getEmail());
                        System.out.println("👋 Até logo, " + currentUser.getEmail());
                        currentUser = null;
                    }
                    case 0 -> {
                        System.out.println("Encerrando sistema...");
                        sistemaRodando = false;
                    }
                    default -> System.out.println("❌ Opção inválida.");
                }
            }
        }
    }

    // --- Métodos Auxiliares de Exibição ---

    private void exibirMenuVisitante() {
        System.out.println("\n--- MENU PRINCIPAL (VISITANTE) ---");
        System.out.println("1. Entrar (Login)");
        System.out.println("2. Criar Nova Conta");
        System.out.println("3. Listar Usuários Cadastrados");
        System.out.println("0. Sair");
        System.out.print("👉 Escolha uma opção: ");
    }

    private void exibirMenuLogado() {
        System.out.println("\n--- MENU DO USUÁRIO ---");
        System.out.println("1. Meu Perfil (Criar/Editar + Upload Foto)");
        System.out.println("2. Novo Projeto");
        System.out.println("3. Comentar em Projetos");
        System.out.println("--------------------------------");
        System.out.println("4. [ADMIN] Relatório Analítico (JDBC)");
        System.out.println("5. [ADMIN] Auditoria de Logs (MongoDB)");
        System.out.println("--------------------------------");
        System.out.println("6. Logout (Sair da conta)");
        System.out.println("0. Fechar Sistema");
        System.out.print("👉 Escolha uma opção: ");
    }

    private int lerOpcao(Scanner scanner) {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}