package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.ProfileDAOImpl;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;

public class MainProfileSave {
    public static void main(String[] args) {
        ProfileDAOImpl dao = new ProfileDAOImpl();
        Profile profile = new Profile();

        // REGRA 13: Usando nanoTime para garantir que o nomeCompleto seja diferente,
        // caso haja uma restrição de unicidade oculta.
        String uniqueName = "Usuário Teste " + System.nanoTime();

        profile.setNome(uniqueName);
        profile.setBiografia("Sou um desenvolvedor de testes JPA.");

        try {
            dao.save(profile);
            System.out.println("✅ PERFIL SALVO COM SUCESSO.");
            System.out.println("ID: " + profile.getId() + " | Nome: " + profile.getNome());
        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA ao salvar perfil: " + e.getMessage());
        }
    }
}