package ifpb.edu.br.portfolio.service;

import ifpb.edu.br.portfolio.model.SystemLog;
import ifpb.edu.br.portfolio.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public void registrarLog(String acao, String descricao, String emailUsuario) {
        // Isso roda assíncrono? Num sistema real sim, aqui vamos fazer simples.
        try {
            SystemLog log = new SystemLog(acao, descricao, emailUsuario);
            logRepository.save(log);
            // Não vamos imprimir nada no console para não poluir, o log é silencioso
        } catch (Exception e) {
            System.err.println("Falha ao salvar log no Mongo: " + e.getMessage());
        }
    }

    public List<SystemLog> listarTodos() {
        // Busca do mais recente para o mais antigo
        return logRepository.findAll();
    }
}