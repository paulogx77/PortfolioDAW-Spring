package ifpb.edu.br.portfolio.dao.impl;

import ifpb.edu.br.portfolio.dao.CommentDAO;
import ifpb.edu.br.portfolio.model.Comment;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CommentDAOImpl extends AbstractDAOImpl<Comment, Long> implements CommentDAO {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("portfolio");

    public CommentDAOImpl() {
        super(Comment.class, EMF);
    }

}
