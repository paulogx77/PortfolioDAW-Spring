package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cargo;

    private String imagemUrl;

    @Column(columnDefinition = "TEXT")
    private String biografia;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    // Regra 11: hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Regra 12: toString
    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", nomeCompleto='" + nome + '\'' +
                ", bio='" + (biografia != null && biografia.length() > 30 ? biografia.substring(0, 27) + "..." : biografia) + '\'' +
                '}';
    }
}