package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto; // Mantendo o nome 'texto' que corrigimos

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // --- RELACIONAMENTOS ADICIONADOS (REGRA 2) ---

    // Muitos Comments pertencem a um User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Muitos Comments pertencem a um Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // --- FIM DOS RELACIONAMENTOS ---

    public Comment() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    // Getters/Setters para os relacionamentos
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }


    // Regra 10: equals (não muda)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    // Regra 11: hashCode (não muda)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Regra 12: toString (MODIFICADO PARA EVITAR RECURSÃO)
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", texto='" + (texto.length() > 30 ? texto.substring(0, 27) + "..." : texto) + '\'' +
                ", dataCriacao=" + dataCriacao +
                // Não inclua 'user' ou 'project' aqui!
                '}';
    }
}