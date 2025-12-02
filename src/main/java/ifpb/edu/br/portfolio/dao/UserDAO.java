package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.User;
import java.util.List;
import java.util.Map;

public interface UserDAO extends DAO<User, Long> {
    User findByEmail(String email) throws PersistenciaDawException;
    List<Map<String, Object>> buscarRelatorioGeral();
}