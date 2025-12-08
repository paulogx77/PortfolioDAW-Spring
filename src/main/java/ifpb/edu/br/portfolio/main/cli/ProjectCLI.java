package ifpb.edu.br.portfolio.main.cli;

import ifpb.edu.br.portfolio.dao.CommentDAO;
import ifpb.edu.br.portfolio.dao.ProjectDAO;
import ifpb.edu.br.portfolio.model.Comment;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ProjectCLI {

    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private CommentDAO commentDAO;

    // Método para CADASTRAR PROJETO
    public void cadastrarProjeto(Scanner scanner, User usuarioLogado) {
        // Verifica se o usuário tem perfil antes de deixar criar projeto
        if (usuarioLogado.getProfile() == null) {
            System.out.println("⚠️ Você precisa criar um perfil antes de postar projetos.");
            return;
        }

        try {
            System.out.println("\n--- NOVO PROJETO ---");
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Descrição: ");
            String desc = scanner.nextLine();

            Project project = new Project();
            project.setTitulo(titulo);
            project.setDescricao(desc);
            // Vincula ao perfil do usuário logado
            project.setProfile(usuarioLogado.getProfile());

            projectDAO.save(project);
            System.out.println("✅ Projeto salvo no seu portfólio!");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    // Método para COMENTAR (CORRIGIDO AQUI)
    // Adicionei o parâmetro 'User usuarioLogado' na assinatura do método
    public void comentarProjeto(Scanner scanner, User usuarioLogado) {
        try {
            System.out.println("\n--- COMENTAR ---");
            System.out.print("ID do Projeto: ");
            Long idProj = Long.parseLong(scanner.nextLine());

            Project projeto = projectDAO.getByID(idProj);
            if (projeto == null) {
                System.out.println("❌ Projeto não encontrado.");
                return;
            }

            System.out.print("Seu Comentário: ");
            String texto = scanner.nextLine();

            Comment comment = new Comment();
            comment.setTexto(texto);
            comment.setProject(projeto);

            // Agora a variável 'usuarioLogado' existe e pode ser usada
            comment.setUser(usuarioLogado);

            commentDAO.save(comment);
            System.out.println("✅ Comentário enviado!");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}