package br.com.mello.springscreenmatch.model;

import br.com.mello.springscreenmatch.service.ConsultaGemini;

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

    public String getTitulo() {
        return titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public String getDuracaoMedia() {
        return duracaoMedia;
    }

    public Categoria getGenero() {
        return genero;
    }

    public String getAtores() {
        return atores;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getPaisDeOrigem() {
        return paisDeOrigem;
    }

    public String getURL_POSTER() {
        return URL_POSTER;
    }

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.sinopse = ConsultaGemini.obterTraducao(dadosSerie.sinopse()).trim();
        this.duracaoMedia = dadosSerie.duracaoMedia();
        this.paisDeOrigem = dadosSerie.paisDeOrigem();
        this.URL_POSTER = dadosSerie.URL_POSTER();
    }

    @Override
    public String toString() {
        return String.format("""
                Título: %s
                Gênero: %s
                Data de Lançamento: %s
                Total de Temporadas: %s
                Duração média: %s
                Sinospe: %s
                Origem: %s
                Avaliação: %s
                Poster: %s
                """, this.titulo, this.genero, this.dataLancamento, this.totalTemporadas,
                this.duracaoMedia, this.sinopse, this.paisDeOrigem, this.avaliacao, this.URL_POSTER);
    }
}














