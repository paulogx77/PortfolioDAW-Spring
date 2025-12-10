package ifpb.edu.br.portfolio.main.cli;

import ifpb.edu.br.portfolio.model.SystemLog;
import ifpb.edu.br.portfolio.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class LogCLI {

    @Autowired
    private LogService logService;

    public void exibirLogsAudit() {
        System.out.println("\n--- AUDITORIA DO SISTEMA (MONGODB) ---");
        List<SystemLog> logs = logService.listarTodos();

        if (logs.isEmpty()) {
            System.out.println("Nenhum registro de log encontrado.");
        } else {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM HH:mm:ss");

            for (SystemLog log : logs) {
                System.out.printf("[%s] %-15s | %s (User: %s)%n",
                        log.getTimestamp().format(fmt),
                        log.getAction(),
                        log.getDescription(),
                        (log.getUserEmail() != null ? log.getUserEmail() : "Anônimo")
                );
            }
        }
        System.out.println("----------------------------------------");
    }
}