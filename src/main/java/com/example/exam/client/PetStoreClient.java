package com.example.exam.client;

import com.example.exam.model.Pet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PetStoreClient {

    private final RestTemplate restTemplate;

    @Value("${api.url-externa}")
    private String urlExterna;

    public PetStoreClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Metodo para consumir el servicio GET /pet/{petId} de Petstore.
    public Pet getPetById(Long petId) {
        String url = urlExterna + "/pet/" + petId;
        return restTemplate.getForObject(url, Pet.class);
    }

    //Metodo POST para dar de alta una mascota
    public Pet addPet(Pet pet){
        
        String url = urlExterna + "/pet/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Pet> request = new HttpEntity<>(pet, headers);
        
        return restTemplate.exchange(url, HttpMethod.POST, request, Pet.class).getBody();
    }
}
