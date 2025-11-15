package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.impl.UserDAOImpl;
import ifpb.edu.br.portfolio.dao.impl.ProjectDAOImpl;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.model.Project;

public class MainProjectSave {
    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();

        try {
            // 1. Criar e salvar um User (dono) primeiro
            User owner = new User();
            owner.setEmail("dono.projeto." + System.nanoTime() + "@ifpb.edu.br");
            owner.setSenha("123");
            userDAO.save(owner);
            System.out.println("Dono do projeto salvo (ID: " + owner.getId() + ")");

            // 2. Criar o Project
            Project project = new Project();
            project.setTitle("Meu Novo Projeto de DAW");
            project.setDescription("Descrição do projeto com relacionamentos.");

            // 3. CONECTAR OS OBJETOS (REGRA 4)
            // Define o 'owner' no 'project' (lado 'dono' do relacionamento ManyToOne)
            project.setOwner(owner);

            // 4. Salvar o Project
            projectDAO.save(project);

            // Nota: Não precisamos salvar o 'owner' de novo,
            // mas se tivéssemos adicionado o projeto à lista do 'owner',
            // poderíamos salvar o 'owner' para atualizar a relação.
            // O CascadeType.ALL no User fará o save do projeto se o salvarmos.

            System.out.println("✅ PROJETO SALVO COM SUCESSO.");
            System.out.println("Projeto ID: " + project.getId() + " | Dono ID: " + owner.getId());

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA: " + e.getMessage());
            e.printStackTrace();
        }
    }
}