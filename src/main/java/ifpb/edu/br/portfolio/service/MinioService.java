package ifpb.edu.br.portfolio.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName;

    // Injeta os valores do application.properties
    public MinioService(
            @Value("${minio.url}") String url,
            @Value("${minio.access-key}") String accessKey,
            @Value("${minio.secret-key}") String secretKey,
            @Value("${minio.bucket-name}") String bucketName) {

        this.bucketName = bucketName;

        // Inicializa o cliente do MinIO
        this.minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }

    public String uploadFile(String caminhoArquivoLocal) {
        try {
            // 1. Verifica se o bucket existe, se não, cria
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("ℹ️ Bucket '" + bucketName + "' criado no MinIO.");
            }

            // 2. Prepara o arquivo para leitura
            Path path = Path.of(caminhoArquivoLocal);
            String nomeArquivoOriginal = path.getFileName().toString();
            // Gera um nome único para não sobrescrever (ex: timestamp_foto.png)
            String nomeArquivoMinio = System.currentTimeMillis() + "_" + nomeArquivoOriginal;

            // 3. Faz o Upload
            try (InputStream stream = new FileInputStream(path.toFile())) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(nomeArquivoMinio)
                                .stream(stream, -1, 10485760) // Parte de 10MB
                                .contentType("image/jpeg") // Assumindo imagem, ou detecte dinamicamente
                                .build());
            }

            // 4. Retorna a URL (Como o bucket é privado por padrão, retornamos o nome para montar a URL ou URL direta)
            // Para simplificar neste teste, vamos retornar a URL direta de acesso localhost
            return "http://localhost:9000/" + bucketName + "/" + nomeArquivoMinio;

        } catch (Exception e) {
            System.err.println("❌ Erro no upload MinIO: " + e.getMessage());
            return null; // Retorna null se falhar
        }
    }
}