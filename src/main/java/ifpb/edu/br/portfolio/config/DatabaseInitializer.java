package ifpb.edu.br.portfolio.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource; // Importante
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseInitializer {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void inicializarPLSQL() {
        System.out.println("🔄 Verificando e Atualizando Funções PL/pgSQL e PostGIS...");

        try (Connection connection = dataSource.getConnection()) {
            // Usamos EncodedResource para garantir UTF-8
            EncodedResource resource = new EncodedResource(new ClassPathResource("sql/postgis_setup.sql"));

            // A MÁGICA ESTÁ AQUI:
            // O argumento ";;" diz para o Spring só quebrar o comando quando encontrar dois pontos e vírgulas.
            ScriptUtils.executeSqlScript(
                    connection,
                    resource,
                    false, // continueOnError
                    false, // ignoreFailedDrops
                    "--",  // commentPrefix
                    ";;",  // separator (NOSSO SEPARADOR PERSONALIZADO)
                    "/*",  // blockCommentStartDelimiter
                    "*/"   // blockCommentEndDelimiter
            );

            System.out.println("✅ Script PL/pgSQL executado com sucesso!");
        } catch (SQLException e) {
            System.err.println("❌ Erro de SQL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Erro ao ler/executar script: " + e.getMessage());
        }
    }
}