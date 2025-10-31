package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.dao.impl.ProfileDAOImpl;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ProfileDAOImpl extends AbstractDAOImpl<Profile, Long> implements ProfileDAO {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("portfolio");
    // Construtor obrigatório para a classe base
    public ProfileDAOImpl() {
        super(Profile.class, EMF);
    }

    // NENHUM OUTRO MÉTODO DEVE SER IMPLEMENTADO AQUI, exceto métodos customizados
    // (como findByNomeCompleto, se você o tivesse adicionado à ProfileDAO).
}