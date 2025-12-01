package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication; // Importe a classe principal do projeto
import ifpb.edu.br.portfolio.dao.CommentDAO;
import ifpb.edu.br.portfolio.model.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class MainCommentDeleteAll {

    public static void main(String[] args) {
        // 1. INICIALIZA O SPRING MANUALMENTE
        // Isso carrega as configurações do banco e cria os DAOs
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. RECUPERA O DAO DO SPRING
            // Substitui 'new CommentDAOImpl()' pela injeção real
            CommentDAO dao = context.getBean(CommentDAO.class);

            System.out.println("--- INICIANDO REMOÇÃO TOTAL DE COMENTÁRIOS ---");

            // 3. BUSCAR TODOS
            List<Comment> comments = dao.getAll();

            if (comments.isEmpty()) {
                System.out.println("⚠️ Nenhum comentário encontrado para remoção.");
            } else {
                System.out.println("Encontrados " + comments.size() + " comentários. Iniciando exclusão...");

                // 4. DELETAR UM POR UM
                for (Comment comment : comments) {
                    dao.delete(comment.getId());
                    System.out.println("🗑️ Removido comentário ID: " + comment.getId());
                }

                System.out.println("✅ REMOÇÃO DE TODOS OS COMENTÁRIOS CONCLUÍDA.");
            }

        } catch (Exception e) {
            System.err.println("❌ ERRO NO PROCESSO:");
            e.printStackTrace();
        } finally {
            // 5. ENCERRA O SPRING
            context.close();
        }
    }
}