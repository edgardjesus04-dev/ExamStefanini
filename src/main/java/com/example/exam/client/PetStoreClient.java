package com.example.exam.client;

import com.example.exam.model.Pet;
import com.example.exam.service.PetServiceImpl;
import com.example.exam.utilities.ExternalServiceException;
import com.example.exam.utilities.PetNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class PetStoreClient {
    private static final Logger LOG = LoggerFactory.getLogger(PetStoreClient.class);
    private final RestTemplate restTemplate;

    @Value("${api.url-externa}")
    private String urlExterna;

    public PetStoreClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Metodo para consumir el servicio GET /pet/{petId} de Petstore.
    public Pet getPetById(Long petId) {
        try{
            String url = urlExterna + "/pet/" + petId;
            return restTemplate.getForObject(url, Pet.class);
        }catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOG.error("Mascota no encontrada, petId: {}", petId);
                throw new PetNotFoundException("No se encontró la mascota con id: " + petId);
            }
            LOG.error("Error 4xx del servicio externo Petstore: {}", ex.getMessage());
            throw new ExternalServiceException("Error al consultar el servicio de mascotas");

        } catch (HttpServerErrorException ex) {
            LOG.error("Error 5xx del servicio externo Petstore: {}", ex.getMessage());
            throw new ExternalServiceException("El servicio de mascotas no está disponible en este momento");

        } catch (ResourceAccessException ex) {
            LOG.error("Timeout o error de conexión con Petstore: {}", ex.getMessage());
            throw new ExternalServiceException("El servicio de mascotas no respondió a tiempo");
        }

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
