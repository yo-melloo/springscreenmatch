package br.com.mello.springscreenmatch.model;

import java.util.Collections;
import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private String dataLancamento;
    private String duracaoMedia;
    private Categoria genero;
    private String atores;
    private String sinopse;
    private String paisDeOrigem;
    private String URL_POSTER;

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.sinopse = dadosSerie.sinopse();
        this.duracaoMedia = dadosSerie.duracaoMedia();
        this.paisDeOrigem = dadosSerie.paisDeOrigem();
        this.URL_POSTER = dadosSerie.URL_POSTER();
    }
}














