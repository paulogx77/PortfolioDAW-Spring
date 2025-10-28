package ifpb.edu.br.portfolio.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Data
@Entity
@Table(name = "users") // "user" é uma palavra reservada em muitos bancos, então "users" é mais seguro
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Verifica se o ID é nulo (entidade nova) ou se a classe é diferente
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        // A igualdade entre entidades é definida pelo seu identificador (ID)
        return Objects.equals(id, user.id);
    }

    // Regra 11: hashCode (baseado apenas no ID, padrão JPA)
    @Override
    public int hashCode() {
        // Gera o hash usando apenas o ID
        return Objects.hash(id);
    }

    // Regra 12: toString (Inclui campos importantes)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }

}