package ifpb.edu.br.portfolio.repository;

import ifpb.edu.br.portfolio.model.SystemLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<SystemLog, String> {
    // O Spring Data Mongo cria isso automaticamente, igual ao JPA
    List<SystemLog> findByAction(String action);
}