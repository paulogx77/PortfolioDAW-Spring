package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projeto")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "imagem_capaurl")
    private String imagemCapaUrl;

    @Column(name = "data_pub")
    private LocalDate dataPub = LocalDate.now();

    // VINCULO CORRETO: Projeto pertence a um Perfil
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Profile profile;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Project() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getImagemCapaUrl() { return imagemCapaUrl; }
    public void setImagemCapaUrl(String imagemCapaUrl) { this.imagemCapaUrl = imagemCapaUrl; }
    public LocalDate getDataPub() { return dataPub; }
    public void setDataPub(LocalDate dataPub) { this.dataPub = dataPub; }

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}