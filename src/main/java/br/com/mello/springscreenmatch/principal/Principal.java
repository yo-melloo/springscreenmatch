package br.com.mello.springscreenmatch.principal;

import br.com.mello.springscreenmatch.model.DadosEpisodio;
import br.com.mello.springscreenmatch.model.DadosSerie;
import br.com.mello.springscreenmatch.model.DadosTemporada;
import br.com.mello.springscreenmatch.service.ConsumoApi;
import br.com.mello.springscreenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        // Imprime todas as temporadas:
        //temporadas.forEach(System.out::println);

        //Esse pior ainda:
        //Imprime todos os espisódios (apenas os títulos):
        //Mello do futuro corrigindo:
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        // funcao('(argumentos)' -> 'corpo-da-função')
        // funcao(a, b -> { return a + b; })

        //Usando streams para imprimir top 5 episódios:
        //Criando a lista de Episódios:
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                //flatMap cria um novo array *
                .collect(Collectors.toList());
                //collect(Collectors.tolist()) converte o array em uma lista
                //Acredito que seja por se tratar do tipo de dado adequado para se por em uma List, dããã...

        //dadosEpisodios.forEach(System.out::println);
        System.out.printf("%nTop 5 ep:");
        dadosEpisodios.stream()
                .filter(e -> !e.classificacao().equalsIgnoreCase("n/a"))
                .sorted(Comparator.comparing(DadosEpisodio::classificacao).reversed())
                .limit(5)
                .forEach(System.out::println);

    }

}
