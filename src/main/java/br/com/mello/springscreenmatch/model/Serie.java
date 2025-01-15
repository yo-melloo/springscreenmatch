package br.com.mello.springscreenmatch.model;

import br.com.mello.springscreenmatch.service.ConsultaGemini;
import jakarta.persistence.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private String dataLancamento;
    private String duracaoMedia;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String sinopse;
    private String paisDeOrigem;
    private String URL_POSTER;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Episodio> episodios = new ArrayList<>();

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

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

    public long getId() {
        return id;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
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

    public Serie() {
    }

    @Override
    public String toString() {
        return String.format("""
                ########################
                Titulo: %s
                Genero: %s
                Ano de Lancamento: %s
                Sinopse: %s
                Atores: %s
                Classificação: %s
                Poster: %s
                Episódios: %s
                """,
                this.titulo,
                this.genero,
                this.dataLancamento,
                this.sinopse,
                this.atores,
                this.avaliacao,
                this.URL_POSTER,
                this.episodios);
    }
}