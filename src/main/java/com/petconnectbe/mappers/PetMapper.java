package com.petconnectbe.mappers;

import com.petconnectbe.dto.PetDto;
import com.petconnectbe.models.Cat;
import com.petconnectbe.models.Dog;
import com.petconnectbe.models.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public Pet toEntity(PetDto dto) {
        if (dto == null) {
            return null;
        }

        Pet pet;
        // Decide qual entidade instanciar com base no petType
        if ("DOG".equalsIgnoreCase(dto.getPetType())) {
            Dog dog = new Dog();
            dog.setBreed(dto.getBreed());
            dog.setSize(dto.getSize());
            pet = dog;
        } else if ("CAT".equalsIgnoreCase(dto.getPetType())) {
            Cat cat = new Cat();
            cat.setBreed(dto.getBreed());
            cat.setCoatType(dto.getCoatType());
            pet = cat;
        } else {
            throw new IllegalArgumentException("Tipo de pet inválido: " + dto.getPetType());
        }

        // Mapeia os campos comuns
        pet.setId(dto.getId());
        pet.setName(dto.getName());
        pet.setWeight(dto.getWeight());
        pet.setAge(dto.getAge());
        pet.setSex(dto.getSex());
        pet.setMicrochipNumber(dto.getMicrochipNumber());
        pet.setAbout(dto.getAbout());
        pet.setImageUrl(dto.getImageUrl());

        return pet;
    }

    public PetDto toDto(Pet pet) {
        if (pet == null) {
            return null;
        }

        PetDto dto = new PetDto();

        // Mapeia os campos comuns
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setWeight(pet.getWeight());
        dto.setAge(pet.getAge());
        dto.setSex(pet.getSex());
        dto.setMicrochipNumber(pet.getMicrochipNumber());
        dto.setAbout(pet.getAbout());
        dto.setImageUrl(pet.getImageUrl());
        if (pet.getTutor() != null) {
            dto.setTutorId(pet.getTutor().getId());
        }

        // Mapeia os campos específicos e o petType
        if (pet instanceof Dog) {
            Dog dog = (Dog) pet;
            dto.setPetType("DOG");
            dto.setBreed(dog.getBreed());
            dto.setSize(dog.getSize());
        } else if (pet instanceof Cat) {
            Cat cat = (Cat) pet;
            dto.setPetType("CAT");
            dto.setBreed(cat.getBreed());
            dto.setCoatType(cat.getCoatType());
        }

        return dto;
    }
}
