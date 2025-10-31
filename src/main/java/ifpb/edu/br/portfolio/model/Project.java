package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    private String imagemCapaUrl;

    @Column(name = "data_publicacao", updatable = false)
    private LocalDateTime dataPublicacao = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    // Regra 11: hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Regra 12: toString
    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + titulo + '\'' +
                ", dataCriacao=" + dataPublicacao +
                '}';
    }
}