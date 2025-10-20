package com.petconnectbe.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Define o contrato para o serviço de armazenamento de arquivos, como imagens de pets.
 * <p>
 * Abstrai a lógica de como e onde os arquivos são fisicamente armazenados (por exemplo,
 * em um sistema de arquivos local, em um bucket de nuvem como Amazon S3, etc.),
 * expondo uma operação de armazenamento simples.
 */
public interface FileStorageService {

    /**
     * Armazena um arquivo enviado e retorna um identificador de acesso a ele.
     * <p>
     * A implementação deste método deve lidar com a recepção do {@link MultipartFile},
     * salvá-lo em um local de armazenamento persistente e seguro, e retornar uma
     * string (como uma URL ou um nome de arquivo único) que possa ser usada
     * posteriormente para acessar o arquivo.
     *
     * @param file O arquivo a ser armazenado, recebido de um upload de formulário.
     * @return Uma {@code String} representando o caminho ou a URL para o arquivo armazenado.
     *         Este valor será usado para associar o arquivo a outras entidades, como um Pet.
     */
    String store(MultipartFile file);
}
