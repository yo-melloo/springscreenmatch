package br.com.mello.springscreenmatch.principal;

import br.com.mello.springscreenmatch.model.DadosSerie;
import br.com.mello.springscreenmatch.model.DadosTemporada;
import br.com.mello.springscreenmatch.service.ConsumoApi;
import br.com.mello.springscreenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final String ENDERECO = "https://www.omdbapi.com/?apikey=";
    private final String API_KEY = "1a6bcfd8";
    private final ConsumoApi consultaAPI = new ConsumoApi();
    private final Scanner entrada = new Scanner(System.in);
    private final ConverteDados conversor = new ConverteDados();

    public void exibirMenu(){
        //Pedindo titulo para pesquisa:
        System.out.print("Digite nome de um filme ou série: ");
        var tituloPesquisado = entrada.nextLine();
        String uri = ENDERECO + API_KEY + "&t=" + tituloPesquisado.replace(" ", "+");

        //Pesquisando com a API:
        var jsonResultado = consultaAPI.realizarConsulta(uri);

        //Atribuindo dados do Json ao record:
        DadosSerie dadosSerie = conversor.jsonParaObjeto(jsonResultado, DadosSerie.class);
        System.out.println("Resultado da série: " + dadosSerie);

        //Listando temporadas:
        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i < dadosSerie.totalTemporadas(); i++) {
            String jsonResultadoTemporada = consultaAPI.realizarConsulta(uri + "&season=" + i);
            DadosTemporada dadosTemporada = conversor.jsonParaObjeto(jsonResultadoTemporada, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        //Ainda não aprendi a sintaxe desse sout:
        temporadas.forEach(System.out::println);

        //Esse pior ainda:
        //Mello do futuro corrigindo:
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        // funcao('(argumentos)' -> 'corpo-da-função')
        // funcao(a, b -> { return a + b; })

    }

}
