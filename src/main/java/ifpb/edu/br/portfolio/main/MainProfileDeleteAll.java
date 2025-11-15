package ifpb.edu.br.portfolio.main;

import ifpb.edu.br.portfolio.dao.impl.ProfileDAOImpl;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import java.util.List;

public class MainProfileDeleteAll {
    public static void main(String[] args) {
        ProfileDAOImpl dao = new ProfileDAOImpl();

        try {
            List<Profile> profiles = dao.getAll();
            if (profiles.isEmpty()) {
                System.out.println("Nenhum perfil encontrado para remoção.");
                return;
            }

            System.out.println("Removendo " + profiles.size() + " perfis...");

            for (Profile profile : profiles) {
                dao.delete(profile.getId());
                System.out.println("  - Removido perfil ID: " + profile.getId());
            }

            System.out.println("✅ REMOÇÃO DE TODOS OS PERFIS CONCLUÍDA.");

        } catch (PersistenciaDawException e) {
            System.err.println("❌ ERRO DE PERSISTÊNCIA: " + e.getMessage());
        }
    }
}