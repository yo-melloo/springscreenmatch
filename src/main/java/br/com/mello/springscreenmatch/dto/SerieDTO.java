package br.com.mello.springscreenmatch.dto;

import br.com.mello.springscreenmatch.model.Categoria;

public record SerieDTO (
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero,
        String atores,
        String poster,
        String sinopse
){}