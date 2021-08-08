package com.example.cachetesting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response implements Serializable {

    private static final long serialVersionUID = -8628536561934610870L;

    public static final String CACHE_NAME = "response";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long identity;

    private String name;
}
