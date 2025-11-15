package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.impl.UserDAOImpl;
import ifpb.edu.br.portfolio.dao.impl.ProjectDAOImpl;
import ifpb.edu.br.portfolio.dao.impl.CommentDAOImpl;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.model.Comment;

public class MainCommentSave {
    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        CommentDAOImpl commentDAO = new CommentDAOImpl();

        try {
            // 1. Criar e salvar um User (autor do comentário)
            User author = new User();
            author.setEmail("autor.comentario." + System.nanoTime() + "@ifpb.edu.br");
            author.setSenha("123");
            userDAO.save(author);
            System.out.println("Autor salvo (ID: " + author.getId() + ")");

            // 2. Criar e salvar um Project (para ser comentado)
            // Este projeto precisa de um dono, pode ser o mesmo autor ou outro
            Project project = new Project();
            project.setTitle("Projeto que será comentado");
            project.setDescription("Descrição...");
            project.setOwner(author); // Usando o mesmo 'author' como 'owner'
            projectDAO.save(project);
            System.out.println("Projeto salvo (ID: " + project.getId() + ")");

            // 3. Criar o Comment
            Comment comment = new Comment();
            comment.setTexto("Ótimo projeto, parabéns!");

            // 4. CONECTAR OS OBJETOS (REGRA 4)
            comment.setUser(author);     // Define o autor do comentário
            comment.setProject(project); // Define o projeto comentado

            // 5. Salvar o Comment
            commentDAO.save(comment);

            System.out.println("✅ COMENTÁRIO SALVO COM SUCESSO.");
            System.out.println("Comentário ID: " + comment.getId());

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA: " + e.getMessage());
            e.printStackTrace();
        }
    }
}