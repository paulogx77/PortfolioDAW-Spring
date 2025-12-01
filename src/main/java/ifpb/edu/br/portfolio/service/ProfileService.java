package ifpb.edu.br.portfolio.service;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileDAO profileDAO;

    // CORREÇÃO: Injetamos o DAO via construtor (Spring faz o trabalho)
    // Antes você devia ter algo como: private ProfileDAO dao = new ProfileDAOImpl(); <- ISSO CAUSA O ERRO
    @Autowired
    public ProfileService(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    public Profile save(Profile profile) throws PersistenciaDawException {
        // Validações de regra de negócio podem ficar aqui
        return profileDAO.save(profile);
    }

    public Profile findById(Long id) throws PersistenciaDawException {
        return profileDAO.getByID(id);
    }

    public List<Profile> findAll() throws PersistenciaDawException {
        return profileDAO.getAll();
    }

    public void delete(Long id) throws PersistenciaDawException {
        profileDAO.delete(id);
    }

    public Profile update(Profile profile) throws PersistenciaDawException {
        return profileDAO.update(profile);
    }
}