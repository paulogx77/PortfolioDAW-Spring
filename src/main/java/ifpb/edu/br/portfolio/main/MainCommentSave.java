package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.CommentDAOImpl;
import ifpb.edu.br.portfolio.model.Comment;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;

public class MainCommentSave {
    public static void main(String[] args) {
        CommentDAOImpl dao = new CommentDAOImpl();
        Comment comment = new Comment();

        // REGRA 13: Garante conteúdo diferente a cada execução
        String uniqueContent = "Comentário de teste n. " + System.nanoTime();

        comment.setTexto(uniqueContent); // Usando o campo 'texto' corrigido
        // dataCriacao é automático na Entidade

        try {
            dao.save(comment);
            System.out.println("✅ COMENTÁRIO SALVO COM SUCESSO.");
            System.out.println("ID: " + comment.getId() + " | Conteúdo (início): " + comment.getTexto().substring(0, 20) + "...");
        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA ao salvar comentário: " + e.getMessage());
        }
    }
}