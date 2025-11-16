
package com.petconnectbe.controllers;

import com.petconnectbe.dto.AddressDto;
import com.petconnectbe.services.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    /**
     * Busca o endereço associado a um usuário específico.
     * @param userId O ID do usuário cujo endereço será buscado.
     * @return O endereço do usuário ou 404 (Not Found) se não existir.
     */
    @GetMapping
    public ResponseEntity<AddressDto> getAddressByUserId(@PathVariable UUID userId) {
        return addressService.findAddressByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria ou atualiza (upsert) o endereço para um usuário específico.
     * Se o usuário já tiver um endereço, ele será atualizado.
     * Se não, um novo endereço será criado e associado a ele.
     * @param userId O ID do usuário.
     * @param addressDto Os dados do endereço a serem salvos.
     * @return O endereço criado ou atualizado.
     */
    @PutMapping
    public ResponseEntity<AddressDto> createOrUpdateAddress(
            @PathVariable UUID userId,
            @Valid @RequestBody AddressDto addressDto) {
        AddressDto savedAddress = addressService.createOrUpdateAddress(userId, addressDto);
        return ResponseEntity.ok(savedAddress);
    }

    /**
     * Deleta o endereço de um usuário específico.
     * @param userId O ID do usuário cujo endereço será deletado.
     * @return 204 (No Content) em caso de sucesso.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAddressByUserId(@PathVariable UUID userId) {
        addressService.deleteAddressByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
