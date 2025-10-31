package ifpb.edu.br.portfolio.service;

import ifpb.edu.br.portfolio.dao.PersistenciaDawException;
import ifpb.edu.br.portfolio.model.Comment;
import ifpb.edu.br.portfolio.dao.CommentDAO;
import ifpb.edu.br.portfolio.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentRepository;

    public List<Comment> getAllComments() throws PersistenciaDawException {
        return commentRepository.getAll();
    }

    public Optional<Comment> getCommentById(Long id) throws PersistenciaDawException {
        return Optional.ofNullable(commentRepository.getByID(id));
    }

    public Project saveComment(Comment comment) throws PersistenciaDawException {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) throws PersistenciaDawException {
        commentRepository.delete(id);
    }
}
