## Spring Cache

Projeto para reforçar conhecimentos com redis, utilizando o Spring Cache


Trabalhando com Spring Cache, para otimizar consultas repetidas.

### Exemplo de utilização

```java 
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
```