package br.com.mello.springscreenmatch;

import br.com.mello.springscreenmatch.model.DadosTemporada;
import br.com.mello.springscreenmatch.principal.Principal;
import br.com.mello.springscreenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringscreenmatchApplication implements CommandLineRunner {
	@Autowired
	private SerieRepository repositorioSerie;

	public static void main(String[] args) {
		SpringApplication.run(SpringscreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Principal principal = new Principal(repositorioSerie);
		principal.exibirMenu();
	}

}
