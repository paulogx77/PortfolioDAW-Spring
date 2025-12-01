package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.PortfolioApplication; // Importe sua classe principal
import ifpb.edu.br.portfolio.dao.CommentDAO;
import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.dao.ProjectDAO;
import ifpb.edu.br.portfolio.dao.UserDAO;
import ifpb.edu.br.portfolio.model.Comment;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

public class MainCommentSave {

    public static void main(String[] args) {
        // 1. INICIALIZA O SPRING MANUALMENTE
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);

        try {
            // 2. RECUPERA OS DAOS DO SPRING
            UserDAO userDAO = context.getBean(UserDAO.class);
            ProfileDAO profileDAO = context.getBean(ProfileDAO.class);
            ProjectDAO projectDAO = context.getBean(ProjectDAO.class);
            CommentDAO commentDAO = context.getBean(CommentDAO.class);

            System.out.println("--- INICIANDO TESTE: SALVAR COMENTÁRIO ---");

            // -----------------------------------------------------------
            // CENÁRIO:
            // O Usuário "Dono" tem um Perfil e um Projeto.
            // O Usuário "Autor" vai comentar nesse projeto.
            // -----------------------------------------------------------

            // 3. CRIAR O DONO DO PROJETO
            User owner = new User();
            owner.setEmail("dono.projeto." + System.nanoTime() + "@ifpb.edu.br");
            owner.setSenha("123");
            owner.setDataCriacao(LocalDateTime.now());
            userDAO.save(owner);
            System.out.println("✅ Dono do projeto salvo ID: " + owner.getId());

            // 4. CRIAR O PERFIL DO DONO (Necessário para ter projeto)
            Profile profile = new Profile();
            profile.setNome("Perfil do Dono");
            profile.setUser(owner);
            profileDAO.save(profile);
            System.out.println("✅ Perfil do dono salvo ID: " + profile.getId());

            // 5. CRIAR O PROJETO
            Project project = new Project();
            project.setTitulo("Projeto que será comentado"); // Corrigido: setTitulo
            project.setDescricao("Descrição do projeto...");
            project.setProfile(profile); // Corrigido: Liga ao Perfil, não ao User direto
            projectDAO.save(project);
            System.out.println("✅ Projeto salvo ID: " + project.getId());

            // -----------------------------------------------------------

            // 6. CRIAR O AUTOR DO COMENTÁRIO (Outra pessoa)
            User author = new User();
            author.setEmail("comentarista." + System.nanoTime() + "@ifpb.edu.br");
            author.setSenha("abc");
            author.setDataCriacao(LocalDateTime.now());
            userDAO.save(author);
            System.out.println("✅ Autor do comentário salvo ID: " + author.getId());

            // 7. CRIAR O COMENTÁRIO
            Comment comment = new Comment();
            comment.setTexto("Ótimo projeto! Achei a arquitetura muito interessante.");

            // 8. CONECTAR OS OBJETOS
            comment.setUser(author);   // Corrigido: setAuthor (conforme classe Comment)
            comment.setProject(project); // Liga ao projeto criado acima

            // 9. SALVAR O COMENTÁRIO
            commentDAO.save(comment);

            System.out.println("✅ COMENTÁRIO SALVO COM SUCESSO.");
            System.out.println("Comentário ID: " + comment.getId());
            System.out.println("Quem escreveu: " + author.getEmail());
            System.out.println("Onde escreveu: " + project.getTitulo());

        } catch (Exception e) {
            System.err.println("❌ ERRO NO TESTE:");
            e.printStackTrace();
        } finally {
            // 10. FECHA O SPRING
            context.close();
        }
    }
}