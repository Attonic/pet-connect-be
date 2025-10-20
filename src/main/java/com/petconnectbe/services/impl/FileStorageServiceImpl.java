package com.petconnectbe.services.impl;

import com.petconnectbe.services.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

/**
 * Implementação <strong>simulada</strong> do serviço
 * {@link FileStorageService}.
 * <p>
 * <strong>ATENÇÃO:</strong> Esta é uma implementação para fins de
 * desenvolvimento e teste.
 * Ela não armazena fisicamente os arquivos, apenas simula o processo e retorna
 * um caminho fictício.
 * Em um ambiente de produção, esta classe deve ser substituída por uma
 * implementação real
 * que salve os arquivos em um sistema de armazenamento persistente (ex: Amazon
 * S3, Google Cloud Storage, ou disco local).
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

    /**
     * {@inheritDoc}
     * <p>
     * Esta implementação simulada gera um nome de arquivo único e imprime o nome do
     * arquivo original no console para fins de depuração. O caminho retornado é um
     * caminho fictício que pode ser usado pelo front-end para exibir imagens de
     * exemplo.
     */
    @Override
    public String store(MultipartFile file) {
        // Simula o log do armazenamento do arquivo
        System.out.println("Simulando o armazenamento do arquivo: " + file.getOriginalFilename());

        // Gera um nome de arquivo único para evitar conflitos
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Retorna um caminho fictício que o front-end pode usar para exibir uma imagem
        // de exemplo
        return "/static/images/pets/" + fileName;
    }
}
