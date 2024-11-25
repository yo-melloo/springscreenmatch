package br.com.mello.springscreenmatch.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numero;
    private Double classificacao;
    private LocalDate dataDeLancamento;

    public Episodio(Integer temporada, DadosEpisodio dadosEpisodio) {
    this.temporada = temporada;
    this.titulo = dadosEpisodio.titulo();
    this.numero = dadosEpisodio.numero();
        try {
            this.classificacao = Double.valueOf(dadosEpisodio.classificacao());
        } catch (NumberFormatException e) {
            this.classificacao = 0.0;
            //throw new RuntimeException(e);
        }

        try {
            this.dataDeLancamento = LocalDate.parse(dadosEpisodio.dataDeLancamento());
        } catch (Exception e) {
            this.dataDeLancamento = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Double classificacao) {
        this.classificacao = classificacao;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    @Override
    public String toString() {
        return  "temporada: " + temporada +
                ", titulo: " + titulo + '\'' +
                ", numero: " + numero +
                ", classificacao: " + classificacao +
                ", dataDeLancamento: " + dataDeLancamento;
    }
}