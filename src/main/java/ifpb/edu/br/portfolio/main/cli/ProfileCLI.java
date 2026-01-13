package ifpb.edu.br.portfolio.main.cli;

import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class ProfileCLI {

    @Autowired private ProfileDAO profileDAO;
    @Autowired private MinioService minioService;

    public void cadastrarPerfil(Scanner scanner, User usuarioLogado) {
        // ... (Mantenha o código de cadastro exatamente como estava antes) ...
        // Vou omitir aqui para focar na mudança do método de busca,
        // mas não apague o método cadastrarPerfil!
        if (usuarioLogado.getProfile() != null) {
            System.out.println("⚠️ Você já possui um perfil.");
            return;
        }
        // ... (código de cadastro continua igual) ...
        // Se precisar do código completo de cadastro de novo, me avise.

        // --- CÓDIGO RESUMIDO DO CADASTRO (COPIE O DO ANTERIOR SE PRECISAR) ---
        try {
            System.out.println("\n--- CRIAR PERFIL ---");
            System.out.print("Nome: "); String nome = scanner.nextLine();
            System.out.print("Cargo: "); String cargo = scanner.nextLine();
            System.out.print("Bio: "); String bio = scanner.nextLine();

            System.out.print("Latitude: ");
            double lat = Double.parseDouble(scanner.nextLine().replace(",", "."));
            System.out.print("Longitude: ");
            double lon = Double.parseDouble(scanner.nextLine().replace(",", "."));

            System.out.print("Foto (MinIO): "); String foto = scanner.nextLine();
            String url = (!foto.isBlank()) ? minioService.uploadFile(foto) : null;

            Profile p = new Profile();
            p.setNome(nome); p.setCargo(cargo); p.setBiografia(bio);
            p.setLatitude(lat); p.setLongitude(lon); p.setImagemUrl(url);
            p.setUser(usuarioLogado);

            profileDAO.save(p);
            usuarioLogado.setProfile(p);
            System.out.println("✅ Perfil criado!");
        } catch (Exception e) { e.printStackTrace(); }
    }


    // --- MÉTODO ALTERADO PARA AUTOMATIZAÇÃO ---
    public void buscarNetworking(Scanner scanner, User usuarioLogado) {
        try {
            System.out.println("\n--- 📡 RADAR DE NETWORKING (PostGIS) ---");

            double latCentral;
            double lonCentral;
            Profile meuPerfil = usuarioLogado.getProfile();

            // 1. Verifica se o usuário tem coordenadas salvas
            if (meuPerfil != null && meuPerfil.getLatitude() != null && meuPerfil.getLongitude() != null) {
                System.out.println("📍 Detectamos sua localização: " + meuPerfil.getNome());
                System.out.println("   Lat: " + meuPerfil.getLatitude() + " | Lon: " + meuPerfil.getLongitude());
                System.out.print("   Deseja usar essa localização? (S/n): ");
                String opcao = scanner.nextLine();

                if (opcao.equalsIgnoreCase("n")) {
                    // Digitar manualmente
                    System.out.print("Digite a Latitude: ");
                    latCentral = Double.parseDouble(scanner.nextLine().replace(",", "."));
                    System.out.print("Digite a Longitude: ");
                    lonCentral = Double.parseDouble(scanner.nextLine().replace(",", "."));
                } else {
                    // Usar automático
                    latCentral = meuPerfil.getLatitude();
                    lonCentral = meuPerfil.getLongitude();
                }
            } else {
                // Não tem perfil ou não tem coordenadas
                System.out.println("⚠️ Você não tem localização no perfil.");
                System.out.print("Digite a Latitude Central: ");
                latCentral = Double.parseDouble(scanner.nextLine().replace(",", "."));

                System.out.print("Digite a Longitude Central: ");
                lonCentral = Double.parseDouble(scanner.nextLine().replace(",", "."));
            }

            System.out.print("Raio de busca (KM): ");
            double raio = Double.parseDouble(scanner.nextLine());

            // 2. Chama o DAO
            List<Map<String, Object>> vizinhos = profileDAO.buscarProximos(latCentral, lonCentral, raio);

            if (vizinhos.isEmpty()) {
                System.out.println("😔 Ninguém encontrado num raio de " + raio + "km.");
            } else {
                System.out.printf("%-25s | %-20s | %s%n", "NOME", "CARGO", "DISTÂNCIA");
                System.out.println("----------------------------------------------------------------");
                for (Map<String, Object> v : vizinhos) {
                    // Simples lógica para marcar você mesmo na lista
                    String nome = (String) v.get("nome_perfil");
                    String marcador = (meuPerfil != null && nome.equals(meuPerfil.getNome())) ? " (Você)" : "";

                    System.out.printf("%-25s | %-20s | %.2f km%n",
                            nome + marcador,
                            v.get("cargo"),
                            v.get("distancia_km"));
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro na busca: " + e.getMessage());
        }
    }
}