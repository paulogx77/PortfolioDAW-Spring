package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList; // Importar
import java.util.List;     // Importar
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // --- RELACIONAMENTOS ADICIONADOS (REGRA 2) ---

    // Um User tem um Profile
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Profile profile;

    // Um User (owner) tem muitos Projects
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>(); // Inicializar a lista

    // Um User tem muitos Comments
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>(); // Inicializar a lista

    // --- FIM DOS RELACIONAMENTOS ---

    // Construtor padrão
    public User() {}

    // Getters e Setters (Manuais, sem Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    // Getters/Setters para os relacionamentos
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    // Regra 10: equals (baseado apenas no ID, não muda)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    // Regra 11: hashCode (baseado apenas no ID, não muda)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Regra 12: toString (MODIFICADO PARA EVITAR RECURSÃO)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}