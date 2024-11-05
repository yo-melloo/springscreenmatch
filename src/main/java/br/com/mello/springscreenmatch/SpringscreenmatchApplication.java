package br.com.mello.springscreenmatch;

import br.com.mello.springscreenmatch.model.DadosEpisodio;
import br.com.mello.springscreenmatch.model.DadosSerie;
import br.com.mello.springscreenmatch.service.ConsumoApi;
import br.com.mello.springscreenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringscreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringscreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) {
		//Preparando consumidor da API e conversor:
		ConsumoApi consulta = new ConsumoApi();
		ConverteDados conversorDeTipo = new ConverteDados();

		//Pesquisando título:
		String tituloPesquisado = "Stranger Things".replace(" ", "+");
		int episodio = 1;
		int temporada = 1;

		//Montando URI:
		String chaveApi = "1a6bcfd8";
		String enderecoURI = "https://www.omdbapi.com/?apikey=" + chaveApi + "&t=" + tituloPesquisado;
		String enderecoURIEpisodio = enderecoURI + "&season=" + temporada + "&episode=" + episodio;
		System.out.println(enderecoURIEpisodio);
		var jsonResultado = consulta.realizarConsulta(enderecoURI);
		var jsonResultadoDois = consulta.realizarConsulta(enderecoURIEpisodio);


		//Atribuindo dados do Json ao record:
		DadosSerie dadosSerie = conversorDeTipo.jsonParaObjeto(jsonResultado, DadosSerie.class);
		System.out.println("Resultado da série: " + dadosSerie);
		DadosEpisodio dadosEpisodio = conversorDeTipo.jsonParaObjeto(jsonResultadoDois, DadosEpisodio.class);
		System.out.println("Resultado do episódio" + dadosEpisodio);

	}

}
