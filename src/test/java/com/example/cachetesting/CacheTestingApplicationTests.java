package com.example.cachetesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheTestingApplicationTests {

	@Autowired
	TestService testService;

	@Autowired
	CacheManager cacheManager;

	@AfterEach
	@BeforeEach
	void setUp() {
		cacheManager.getCacheNames()
				.parallelStream()
				.forEach(s -> {
					System.out.printf("clear: %s%n", s);
					Objects.requireNonNull(cacheManager.getCache(s)).clear();
				});
	}

	@Test
	void shouldValidateResponseCacheablePersistence() {
		testService.updateResponse(new Response(1L, "test"));

		Response response1 = testService.getResponse(1L);

		testService.updateResponse(new Response(1L, "test updated"));

		Response response2 = testService.getResponse(1L);

		assertNotEquals(response1, response2);

		testService.updateResponse(new Response(1L, "test2"));

		Response res = testService.getResponse(1L);
		Response res1 = testService.getResponse(1L);

		assertNotEquals(res, response2);
		assertEquals(res, res1);
	}

	@Test
	void shouldDeleteFromCacheWhenEntityWereRemoved() {
		Response test = testService.updateResponse(new Response(null, "test"));

		Response response = testService.getResponse(test.getIdentity());
		Response response1 = testService.getResponse(test.getIdentity());
		assertEquals(response, response1);

		testService.deleteResponse(test.getIdentity());

		assertNull(testService.getResponse(test.getIdentity()));

		assertNull(cacheManager.getCache(Response.CACHE_NAME)
				.get(test.getIdentity())
				.get());
	}

}
