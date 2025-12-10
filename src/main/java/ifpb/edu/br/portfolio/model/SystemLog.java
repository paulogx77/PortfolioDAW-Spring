package ifpb.edu.br.portfolio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "logs") // Nome da coleção (equivale a tabela) no Mongo
public class SystemLog {

    @Id
    private String id; // No Mongo o ID geralmente é String (ObjectId)

    private String action;      // Ex: "LOGIN", "CREATE_PROJECT"
    private String description; // Detalhes
    private String userEmail;   // Quem fez (pode ser null se for erro de sistema)
    private LocalDateTime timestamp;

    public SystemLog() {
        this.timestamp = LocalDateTime.now();
    }

    public SystemLog(String action, String description, String userEmail) {
        this.action = action;
        this.description = description;
        this.userEmail = userEmail;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (User: %s)", timestamp, action, description, userEmail);
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}