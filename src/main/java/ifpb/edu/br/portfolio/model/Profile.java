package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(name = "cargo", nullable = false) // Adicionando o campo 'cargo' que faltava
    private String cargo;

    @Column(columnDefinition = "TEXT")
    private String bio;

    // (Opcional, se você tiver)
    private String imagemUrl;

    // --- RELACIONAMENTO ADICIONADO (REGRA 2) ---

    // Um Profile pertence a um User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true) // Define a chave estrangeira
    private User user;

    // --- FIM DOS RELACIONAMENTOS ---

    public Profile() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // Regra 10: equals (não muda)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    // Regra 11: hashCode (não muda)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Regra 12: toString (MODIFICADO PARA EVITAR RECURSÃO)
    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", cargo='" + cargo + '\'' +
                // Não inclua 'user' aqui!
                '}';
    }
}