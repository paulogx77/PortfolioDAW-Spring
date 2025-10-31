package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String texto;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    // Regra 11: hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Regra 12: toString
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + (texto.length() > 30 ? texto.substring(0, 27) + "..." : texto) + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}