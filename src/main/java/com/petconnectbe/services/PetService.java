package com.petconnectbe.services;

import com.petconnectbe.dto.PetDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Define o contrato para o serviço de gerenciamento de Pets.
 * <p>
 * Esta interface abstrai a lógica de negócio para todas as operações relacionadas a pets,
 * incluindo a criação, recuperação, atualização e exclusão de informações de pets.
 * A implementação deste serviço irá coordenar as interações com a camada de persistência
 * e outros serviços, como o {@link FileStorageService}, para gerenciar os dados completos de um pet.
 */
public interface PetService {

    /**
     * Cria um novo Pet no sistema, associando uma imagem a ele.
     * <p>
     * O processo de criação envolve:
     * <ol>
     *     <li>Validar os dados do {@code petDto}.</li>
     *     <li>Se uma {@code image} for fornecida, ela é armazenada usando o {@link FileStorageService}.</li>
     *     <li>As informações do pet, incluindo a URL da imagem, são salvas no banco de dados.</li>
     * </ol>
     *
     * @param petDto O objeto de transferência de dados (DTO) contendo as informações do novo pet.
     * @param image O arquivo de imagem do pet a ser enviado. Este parâmetro é opcional.
     * @return O {@link PetDto} representando o pet que foi criado e salvo.
     */
    PetDto createPet(PetDto petDto, MultipartFile image);
}
