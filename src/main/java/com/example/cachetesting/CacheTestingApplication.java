package com.example.cachetesting;

import com.example.cachetesting.repository.CustomRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.example.cachetesting.Response.CACHE_NAME;

@EnableCaching
@SpringBootApplication
public class CacheTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheTestingApplication.class, args);
    }

}


@Service
@AllArgsConstructor
class TestService {

    private final CustomRepository responseRepository;

    @SneakyThrows
    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public Response getResponse(Long id) {
        return responseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @CachePut(cacheNames = CACHE_NAME, key = "#response.getIdentity()")
    public Response updateResponse(Response response) {
        return responseRepository.save(response);
    }

    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public void deleteResponse (Long id) {
        responseRepository.deleteById(id);
    }

}