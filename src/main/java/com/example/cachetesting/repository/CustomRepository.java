package com.example.cachetesting.repository;

import com.example.cachetesting.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepository extends JpaRepository<Response, Long> {
}
