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

    @Autowired private AuthCLI authCLI;
    @Autowired private UserCLI userCLI;
    @Autowired private ProfileCLI profileCLI;
    @Autowired private ProjectCLI projectCLI;
    @Autowired private ReportCLI reportCLI;
    @Autowired private LogCLI logCLI;

    @Autowired private LogService logService;

    private User currentUser = null;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean sistemaRodando = true;

        System.out.println("\n=============================================");
        System.out.println("   🚀 SISTEMA PORTFOLIO (FULL STACK CLI) 🚀   ");
        System.out.println("=============================================");
        System.out.println("Stack: Spring | JPA | JDBC | Redis | MinIO | MongoDB | PostGIS");

        while (sistemaRodando) {

            if (currentUser == null) {
                // MODO VISITANTE
                exibirMenuVisitante();
                int opcao = lerOpcao(scanner);

                switch (opcao) {
                    case 1 -> currentUser = authCLI.realizarLogin(scanner);
                    case 2 -> userCLI.cadastrarUsuario(scanner);
                    case 3 -> userCLI.listarUsuarios();
                    case 0 -> {
                        System.out.println("Encerrando...");
                        sistemaRodando = false;
                    }
                    default -> System.out.println("❌ Opção inválida.");
                }

            } else {
                // MODO LOGADO
                System.out.println("\n👤 LOGADO: " + currentUser.getEmail());
                exibirMenuLogado();
                int opcao = lerOpcao(scanner);

                switch (opcao) {
                    case 1 -> profileCLI.cadastrarPerfil(scanner, currentUser);
                    case 2 -> projectCLI.cadastrarProjeto(scanner, currentUser);
                    case 3 -> projectCLI.comentarProjeto(scanner, currentUser);

                    case 4 -> reportCLI.gerarRelatorioGeral(); // JDBC
                    case 5 -> logCLI.exibirLogsAudit();        // MongoDB
                    case 6 -> profileCLI.buscarNetworking(scanner, currentUser); // PostGIS

                    case 7 -> {
                        logService.registrarLog("LOGOUT", "Saiu do sistema", currentUser.getEmail());
                        currentUser = null;
                        System.out.println("👋 Logout efetuado.");
                    }
                    case 0 -> {
                        System.out.println("Encerrando...");
                        sistemaRodando = false;
                    }
                    default -> System.out.println("❌ Opção inválida.");
                }
            }
        }
    }

    private void exibirMenuVisitante() {
        System.out.println("\n--- MENU VISITANTE ---");
        System.out.println("1. Login");
        System.out.println("2. Criar Conta");
        System.out.println("3. Listar Usuários");
        System.out.println("0. Sair");
        System.out.print("👉 Opção: ");
    }

    private void exibirMenuLogado() {
        System.out.println("\n--- MENU USUÁRIO ---");
        System.out.println("1. Meu Perfil (Criar)");
        System.out.println("2. Novo Projeto");
        System.out.println("3. Comentar");
        System.out.println("-----------------------------");
        System.out.println("4. [JDBC] Relatório Projetos");
        System.out.println("5. [MONGO] Logs de Auditoria");
        System.out.println("6. [POSTGIS] Radar Networking");
        System.out.println("-----------------------------");
        System.out.println("7. Logout");
        System.out.println("0. Sair");
        System.out.print("👉 Opção: ");
    }

    private int lerOpcao(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}