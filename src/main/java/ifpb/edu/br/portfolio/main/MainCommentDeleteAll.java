package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.CommentDAOImpl;
import ifpb.edu.br.portfolio.model.Comment;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import java.util.List;

public class MainCommentDeleteAll {
    public static void main(String[] args) {
        CommentDAOImpl dao = new CommentDAOImpl();

        try {
            List<Comment> comments = dao.getAll();
            if (comments.isEmpty()) {
                System.out.println("Nenhum comentário encontrado para remoção.");
                return;
            }

            System.out.println("Removendo " + comments.size() + " comentários...");

            for (Comment comment : comments) {
                dao.delete(comment.getId());
                System.out.println("  - Removido comentário ID: " + comment.getId());
            }

            System.out.println("✅ REMOÇÃO DE TODOS OS COMENTÁRIOS CONCLUÍDA.");

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA: " + e.getMessage());
        }
    }
}