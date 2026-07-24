package com.example.exam.controller;

import com.example.exam.model.Pet;
import com.example.exam.model.PetResponse;
import com.example.exam.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/{idPet}")
    public ResponseEntity<Pet> getPet(@PathVariable("idPet") Long idPet) {
        Pet pet = petService.getPet(idPet);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<PetResponse> addPet(@RequestBody Pet request) {
        PetResponse response = petService.addPet(request);
        return ResponseEntity.ok(response);
    }

}
