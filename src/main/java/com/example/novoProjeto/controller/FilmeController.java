package com.example.novoProjeto.controller;

import com.example.novoProjeto.model.Filme;
import com.example.novoProjeto.repository.FilmeRepository;
import com.example.novoProjeto.response.ResponseHandler;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping(path = "/filme/{codigo}")
    public ResponseEntity consultarFilmePorId(@PathVariable("codigo") Integer codigo) {
        return filmeRepository.findById(codigo)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/filmes")
    public ResponseEntity<Iterable<Filme>> buscarTodosOsFilmes() {
        return ResponseEntity.status(HttpStatus.OK).body(
                filmeRepository.findAll());
    }

    @PostMapping(path = "/filme")
    public ResponseEntity adicionarFilme(@RequestBody Filme filme) {
        if (filmeRepository.existsById(filme.getCodigo())) {
            return ResponseHandler.generateResponse("Filme com o codigo [" + filme.getCodigo() + "] ja cadastrado", HttpStatus.CONFLICT);
        }

        if (filme.getNome().isEmpty() || filme.getGenero().isEmpty() || filme.getSinopse().isEmpty() || filme.getFaixaEtaria().isEmpty()) {
            return ResponseHandler.generateResponse("Campo não pode ser vazio", HttpStatus.BAD_REQUEST);
        }
        var objetoFilme = new Filme();
        objetoFilme.setCodigo(filme.getCodigo());
        objetoFilme.setFaixaEtaria(filme.getFaixaEtaria());
        objetoFilme.setGenero(filme.getGenero());
        objetoFilme.setNome(filme.getNome());
        objetoFilme.setSinopse(filme.getSinopse());

        return ResponseEntity.status(HttpStatus.CREATED).body(filmeRepository.save(objetoFilme));

    }

    @PutMapping(path = "/filme/{codigo}")
    @JsonIgnoreProperties("codigo")
    public ResponseEntity modificarFilme(@PathVariable("codigo") Integer codigo, @RequestBody Filme filme) {
        if (filme.getNome().isEmpty() || filme.getGenero().isEmpty() || filme.getSinopse().isEmpty() || filme.getFaixaEtaria().isEmpty()) {
            return ResponseHandler.generateResponse("Campo não pode ser vazio", HttpStatus.BAD_REQUEST);
        } else {
            Optional<Filme> filmeObject = filmeRepository.findById(codigo);
            if (filmeObject.isPresent()) {
                var filmeEditado = filmeObject.get();
                filmeEditado.setFaixaEtaria(filme.getFaixaEtaria());
                filmeEditado.setGenero(filme.getGenero());
                filmeEditado.setNome(filme.getNome());
                filmeEditado.setSinopse(filme.getSinopse());

                return ResponseEntity.status(HttpStatus.OK).body(filmeRepository.save(filmeEditado));
            } else {
                var objetoFilme = new Filme();
                objetoFilme.setCodigo(codigo);
                objetoFilme.setFaixaEtaria(filme.getFaixaEtaria());
                objetoFilme.setGenero(filme.getGenero());
                objetoFilme.setNome(filme.getNome());
                objetoFilme.setSinopse(filme.getSinopse());
                return ResponseEntity.status(HttpStatus.CREATED).body(filmeRepository.save(objetoFilme));
            }
        }
    }

    @PatchMapping(path = "/filme/{codigo}")
    @JsonIgnoreProperties("codigo")
    public ResponseEntity alteraCampoFilme(@PathVariable("codigo") Integer codigo, @RequestBody Filme filme) {
        Optional<Filme> filmeObject = filmeRepository.findById(codigo);
        if (filmeObject.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("""
                    {
                        "message":"Filme não encontrado"
                    }""");
        }
        var filmeEditado = filmeObject.get();

        if (filme.getNome() != null)
            filmeEditado.setNome(filme.getNome());
        if (filme.getSinopse() != null) {
            filmeEditado.setSinopse(filme.getSinopse());
        }
        if (filme.getGenero() != null)
            filmeEditado.setGenero(filme.getGenero());
        if (filme.getFaixaEtaria() != null)
            filmeEditado.setFaixaEtaria(filme.getFaixaEtaria());

        return ResponseEntity.status(HttpStatus.OK).body(filmeRepository.save(filmeEditado));
    }

    @DeleteMapping(path = "/filme/{codigo}")
    public void deletarFilmePorId(@PathVariable("codigo") Integer codigo) {
        filmeRepository.findById(codigo)
                .map(record -> {
                    filmeRepository.deleteById(codigo);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
