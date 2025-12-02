package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication;
import ifpb.edu.br.portfolio.dao.UserDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Map;

public class MainRelatorioSQL {

    public static void main(String[] args) {
        // 1. Inicia o Spring
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. Pega o DAO
            UserDAO userDAO = context.getBean(UserDAO.class);

            System.out.println("\n--- EXECUTANDO CONSULTA SQL VIA JDBC ---");

            // 3. Chama o método novo que criamos
            List<Map<String, Object>> linhas = userDAO.buscarRelatorioGeral();

            if (linhas.isEmpty()) {
                System.out.println("Nenhum registro encontrado.");
            } else {
                // 4. Imprime os resultados formatados
                System.out.printf("%-40s | %-30s | %s%n", "EMAIL", "PERFIL", "PROJETOS");
                System.out.println("----------------------------------------------------------------------------------");

                for (Map<String, Object> linha : linhas) {
                    // Os nomes das chaves são os nomes das colunas/alias do SQL (usuario_email, nome_perfil...)
                    String email = (String) linha.get("usuario_email");
                    String perfil = (String) linha.get("nome_perfil");
                    Long qtd = (Long) linha.get("qtd_projetos"); // O COUNT retorna Long geralmente

                    System.out.printf("%-40s | %-30s | %d%n", email, (perfil != null ? perfil : "Sem Perfil"), qtd);
                }
            }
            System.out.println("----------------------------------------------------------------------------------\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.close();
        }
    }
}