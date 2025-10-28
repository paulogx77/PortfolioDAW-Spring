package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDAO extends JpaRepository<Profile, Long> {
}
