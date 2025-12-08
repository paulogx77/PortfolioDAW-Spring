package ifpb.edu.br.portfolio.main.cli;

import ifpb.edu.br.portfolio.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReportCLI {

    @Autowired
    private UserDAO userDAO;

    public void gerarRelatorioGeral() {
        try {
            System.out.println("\n--- RELATÓRIO ANALÍTICO (VIA JDBC) ---");
            List<Map<String, Object>> dados = userDAO.buscarRelatorioGeral();

            if (dados.isEmpty()) {
                System.out.println("Nenhum dado encontrado.");
                return;
            }

            System.out.printf("%-30s | %-20s | %s%n", "EMAIL", "PERFIL", "QTD PROJETOS");
            System.out.println("-".repeat(70));

            for (Map<String, Object> linha : dados) {
                System.out.printf("%-30s | %-20s | %d%n",
                        linha.get("usuario_email"),
                        linha.get("nome_perfil"),
                        linha.get("qtd_projetos"));
            }
        } catch (Exception e) {
            System.out.println("❌ Erro no relatório: " + e.getMessage());
        }
    }
}