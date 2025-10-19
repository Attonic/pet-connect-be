# Proposta de Implementação para Armazenamento de Imagens

Este documento descreve a arquitetura e os passos para substituir a implementação simulada (`mock`) do `FileStorageService` por uma solução robusta e pronta para produção usando o **Firebase Cloud Storage**.

---

### Arquitetura Proposta

O fluxo de upload de imagem seguirá os seguintes passos:

1.  **Frontend:** O cliente (app web ou móvel) envia a imagem do pet para a API (`POST /pets`) como parte de uma requisição `multipart/form-data`.
2.  **Backend (API):** O `PetController` recebe a requisição e a repassa para o `PetService`.
3.  **Backend (API):** O `PetService` invoca a nova implementação `FirebaseStorageServiceImpl`.
4.  **FirebaseStorageServiceImpl:**
    *   Recebe o `MultipartFile`.
    *   Autentica-se com o serviço do Firebase usando as credenciais do projeto.
    *   Cria uma referência de armazenamento, definindo um nome de arquivo único (ex: usando UUID) e um caminho (ex: `pet-images/uuid_nomeoriginal.jpg`).
    *   Faz o upload dos bytes do arquivo para o Firebase Cloud Storage.
    *   Após o upload, solicita ao Firebase a URL pública de download do arquivo.
5.  **Retorno e Persistência:**
    *   O `FirebaseStorageServiceImpl` retorna a URL pública para o `PetService`.
    *   O `PetService` atribui essa URL ao campo `imageUrl` da entidade `Pet`.
    *   O `PetRepository` salva a entidade no banco de dados com a URL real da imagem.
6.  **Frontend:** A API retorna o objeto `PetDto` contendo a URL da imagem, que o cliente pode então usar para exibir a foto.

---

### Exemplo de Código (FirebaseStorageServiceImpl.java)

Aqui está um exemplo de como a nova classe de serviço poderia ser implementada. Para isso, seria necessário adicionar a dependência do Firebase Admin SDK ao `pom.xml`.

```java
package com.petconnectbe.services.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.petconnectbe.services.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Implementação real do FileStorageService que utiliza o Firebase Cloud Storage.
 */
@Service // Esta anotação substituiria a da classe "FileStorageServiceImpl" simulada
public class FirebaseStorageServiceImpl implements FileStorageService {

    private Storage storage;
    private final String BUCKET_NAME = "petconnect-project.appspot.com"; // Exemplo de nome do bucket

    /**
     * Inicializa a conexão com o Firebase na inicialização do serviço.
     */
    @PostConstruct
    private void initializeFirebase() throws IOException {
        // Carrega as credenciais a partir de um arquivo de configuração (secure)
        FileInputStream serviceAccount = new FileInputStream("./firebase-credentials.json");

        this.storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .getService();
    }

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Falha ao armazenar arquivo vazio.");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;

            // Define o caminho completo no bucket
            String blobPath = "pet-images/" + uniqueFileName;

            BlobId blobId = BlobId.of(BUCKET_NAME, blobPath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

            // Envia o arquivo para o Firebase Storage
            byte[] fileBytes = file.getBytes();
            storage.create(blobInfo, fileBytes);

            // Retorna a URL pública para acessar o arquivo
            // A URL tem um formato específico, atenção para a versão do Firebase
            return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, blobPath);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao armazenar o arquivo.", e);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
```

### Próximos Passos

1.  **Configurar Firebase:** Criar um projeto no Firebase e ativar o Cloud Storage.
2.  **Credenciais:** Baixar o arquivo de credenciais (`.json`) do Firebase e configurá-lo de forma segura no ambiente do backend.
3.  **Dependência:** Adicionar a dependência do `firebase-admin` no `pom.xml`.
4.  **Implementar:** Substituir o `FileStorageServiceImpl` simulado pelo novo `FirebaseStorageServiceImpl`.
5.  **Testar:** Realizar um teste de ponta-a-ponta enviando uma imagem real.
