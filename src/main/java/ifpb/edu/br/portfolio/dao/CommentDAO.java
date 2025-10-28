package ifpb.edu.br.portfolio.dao;

import ifpb.edu.br.portfolio.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Long> {
}
