package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.ProjectDAOImpl;
import ifpb.edu.br.portfolio.model.Project;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;

public class MainProjectSave {
    public static void main(String[] args) {
        ProjectDAOImpl dao = new ProjectDAOImpl();
        Project project = new Project();

        // REGRA 13: Embora 'title' possa não ser único, é bom para demonstração.
        String uniqueTitle = "Projeto DAW " + System.currentTimeMillis();

        project.setTitulo(uniqueTitle);
        project.setDescricao("Descrição detalhada do projeto de persistência.");
        // dataCriacao é automático na Entidade

        try {
            dao.save(project);
            System.out.println("✅ PROJETO SALVO COM SUCESSO.");
            System.out.println("ID: " + project.getId() + " | Título: " + project.getTitulo());
        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA ao salvar projeto: " + e.getMessage());
        }
    }
}