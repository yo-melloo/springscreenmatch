package br.com.mello.springscreenmatch;

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

		//Realizando consulta na API, "quebrando a consulta em etapas:
		String chaveApi = "1a6bcfd8";
		String tituloPesquisado = "Stranger Things".replace(" ", "+");
		String enderecoURI = "https://www.omdbapi.com/?t=" + tituloPesquisado + "&apikey=" + chaveApi;
		var jsonResultado = consulta.realizarConsulta(enderecoURI);
		System.out.println(jsonResultado);

		//Atribuindo dados do Json ao record
		DadosSerie dadosSerie = conversorDeTipo.jsonParaObjeto(jsonResultado, DadosSerie.class);
		System.out.println("Resultado final: " + dadosSerie);

	}

}
