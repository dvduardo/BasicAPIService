package com.example.novoProjeto.repository;

import com.example.novoProjeto.model.Filme;
import org.springframework.data.repository.CrudRepository;

public interface FilmeRepository extends CrudRepository<Filme,Integer> {
}