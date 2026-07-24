package com.example.exam.service;

import com.example.exam.client.PetStoreClient;
import com.example.exam.model.Pet;
import com.example.exam.model.PetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PetServiceImpl  implements PetService{

    private static final Logger LOG = LoggerFactory.getLogger(PetServiceImpl.class);
    @Autowired
    private PetStoreClient petStoreClient;

    @Override
    public Pet getPet(Long idPet) {
        Pet pet = petStoreClient.getPetById(idPet);
        LOG.info("Respuesta obtenida de Petstore (GET): {}", pet);
        return pet;
    }

    @Override
    public PetResponse addPet(Pet petRequest) {
        Pet petToAdd = new Pet(petRequest.getId(), petRequest.getName(), petRequest.getStatus());

        Pet newPet = petStoreClient.addPet(petToAdd);

        LOG.info("Respuesta obtenida de Petstore (POST): {}", newPet);
        String transactionId = UUID.randomUUID().toString();
        String dateCreated = LocalDateTime.now().toString();

        return new PetResponse(
                transactionId,
                dateCreated,
                newPet.getStatus(),
                newPet.getName()
        );

    }
}
