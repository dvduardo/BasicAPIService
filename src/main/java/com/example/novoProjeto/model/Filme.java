package com.example.novoProjeto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Filme {
    @Id
    @Column(name = "codigo", nullable = false)
    private int codigo;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "sinopse", length = 100)
    private String sinopse;

    @Column(name = "faixaEtaria", length = 100)
    private String faixaEtaria;

    @Column(name = "genero", length = 100)
    private String genero;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
