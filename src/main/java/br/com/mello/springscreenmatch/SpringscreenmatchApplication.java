package br.com.mello.springscreenmatch;

import br.com.mello.springscreenmatch.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@SpringBootApplication
public class SpringscreenmatchApplication implements CommandLineRunner {

	@Override
	public void run(String... args) {
		ConsumoApi consulta = new ConsumoApi();
		String chaveApi = "1a6bcfd8";
		String tituloPesquisado = "Guardians of the Galaxy Vol. 2".replace(" ", "+");
		String endereco = "https://www.omdbapi.com/?s=" + tituloPesquisado + "&apikey=" + chaveApi;

		var resultado = consulta.obterDados(endereco);
		System.out.println(resultado);

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringscreenmatchApplication.class, args);
	}


}
