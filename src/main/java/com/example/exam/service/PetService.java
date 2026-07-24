package com.example.exam.service;

import com.example.exam.model.Pet;
import com.example.exam.model.PetResponse;

public interface PetService {
    public Pet getPet(Long idPet);

    public PetResponse addPet( Pet petRequest);
}

