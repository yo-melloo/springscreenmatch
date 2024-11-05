package br.com.mello.springscreenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias ("Episode") Integer numero,
                            @JsonAlias("imdbRating") Double classificacao,
                            @JsonAlias("Released") String dataDeLancamento) {
}
