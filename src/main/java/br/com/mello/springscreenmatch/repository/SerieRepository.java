package br.com.mello.springscreenmatch.repository;

import br.com.mello.springscreenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
