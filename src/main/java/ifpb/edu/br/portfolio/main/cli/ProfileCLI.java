package ifpb.edu.br.portfolio.main.cli;

import ifpb.edu.br.portfolio.dao.ProfileDAO;
import ifpb.edu.br.portfolio.model.Profile;
import ifpb.edu.br.portfolio.model.User;
import ifpb.edu.br.portfolio.service.MinioService; // Importe o serviço
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Scanner;

@Component
public class ProfileCLI {

    @Autowired private ProfileDAO profileDAO;
    @Autowired private MinioService minioService; // Injeção do MinIO

    public void cadastrarPerfil(Scanner scanner, User usuarioLogado) {
        if (usuarioLogado.getProfile() != null) {
            System.out.println("⚠️ Você já possui um perfil (ID " + usuarioLogado.getProfile().getId() + ").");
            return;
        }

        try {
            System.out.println("\n--- CRIAR PERFIL (COM UPLOAD DE FOTO) ---");
            System.out.print("Nome Completo: ");
            String nome = scanner.nextLine();
            System.out.print("Cargo/Profissão: ");
            String cargo = scanner.nextLine();
            System.out.print("Biografia: ");
            String bio = scanner.nextLine();

            // --- LÓGICA DE UPLOAD ---
            System.out.print("Caminho da foto no seu PC (ex: /home/denis/foto.jpg) ou ENTER para pular: ");
            String caminhoFoto = scanner.nextLine();
            String urlFoto = null;

            if (!caminhoFoto.isBlank()) {
                File arquivo = new File(caminhoFoto);
                if (arquivo.exists() && arquivo.isFile()) {
                    System.out.println("📤 Enviando para o MinIO...");
                    urlFoto = minioService.uploadFile(caminhoFoto);

                    if (urlFoto != null) {
                        System.out.println("✅ Upload concluído: " + urlFoto);
                    }
                } else {
                    System.out.println("⚠️ Arquivo não encontrado. O perfil será salvo sem foto.");
                }
            }
            // ------------------------

            Profile profile = new Profile();
            profile.setNome(nome);
            profile.setCargo(cargo);
            profile.setBiografia(bio);
            profile.setImagemUrl(urlFoto); // Salva a URL do MinIO no banco (Postgres)
            profile.setUser(usuarioLogado);

            profileDAO.save(profile);
            usuarioLogado.setProfile(profile);

            System.out.println("✅ Perfil criado com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}