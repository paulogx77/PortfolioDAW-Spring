package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList; // Importar
import java.util.List;     // Importar
import java.util.Objects;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // 'description'
    private String description;

    // (Opcional, se você tiver)
    private String imagemCapaUrl;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // --- RELACIONAMENTOS ADICIONADOS (REGRA 2) ---

    // Muitos Projects pertencem a um User (owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Um Project tem muitos Comments
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>(); // Inicializar a lista

    // --- FIM DOS RELACIONAMENTOS ---

    public Project() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImagemCapaUrl() { return imagemCapaUrl; }
    public void setImagemCapaUrl(String imagemCapaUrl) { this.imagemCapaUrl = imagemCapaUrl; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    // Getters/Setters para os relacionamentos
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    // Regra 10: equals (não muda)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    // Regra 11: hashCode (não muda)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Regra 12: toString (MODIFICADO PARA EVITAR RECURSÃO)
    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dataCriacao=" + dataCriacao +
                // Não inclua 'owner' ou 'comments' aqui!
                '}';
    }
}