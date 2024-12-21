package br.com.mello.springscreenmatch.principal;

import br.com.mello.springscreenmatch.model.DadosEpisodio;
import br.com.mello.springscreenmatch.model.DadosSerie;
import br.com.mello.springscreenmatch.model.DadosTemporada;
import br.com.mello.springscreenmatch.model.Episodio;
import br.com.mello.springscreenmatch.service.ConsumoApi;
import br.com.mello.springscreenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

        // --Ainda não aprendi a sintaxe desse sout:
        // Imprime todas as temporadas:
        //temporadas.forEach(System.out::println);

        // --Esse pior ainda:
        //Imprime todos os espisódios (apenas os títulos):
        // --Mello do futuro corrigindo:
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        // --funcao('(argumentos)' -> 'corpo-da-função')
        // --funcao(a, b -> { return a + b; })

        //Usando streams para imprimir top 5 episódios:
        //Criando a lista de Episódios:
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                // --flatMap cria um novo array *
                .collect(Collectors.toList());
                // --collect(Collectors.tolist()) converte o array em uma lista
                // --Acredito que seja por se tratar do tipo de dado adequado para se por em uma List, dããã...

        //dadosEpisodios.forEach(System.out::println);
        System.out.printf("%nTop 5 ep:");
        dadosEpisodios.stream() // stream trabalha com coleções de dados
                .filter(e -> !e.classificacao().equalsIgnoreCase("n/a"))
                .sorted(Comparator.comparing(DadosEpisodio::classificacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(t.numero(), e)))
                        .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        //Ver episódio apartir de uma determinada data:
        System.out.print("A partir de que ano você deseja ver os episódios? ");
        var ano = entrada.nextInt();
        entrada.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1,1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataDeLancamento() != null && e.getDataDeLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                                "Temporada: " + e.getTemporada() +
                                " - Episódio: " + e.getNumero() +
                                " - Data de lançamento: " + e.getDataDeLancamento().format(formatador)
                ));

        //Classificando temporadas automaticamente:
        Map<Integer, Double> avaliacoesPorTemp = episodios.stream()
                .filter(e -> e.getClassificacao() > 0.00)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getClassificacao)));

        System.out.println(avaliacoesPorTemp);


        //Coletando Estátisticas
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getClassificacao() > 0.00)
                .collect(Collectors.summarizingDouble(Episodio::getClassificacao));

        System.out.println(est);

        System.out.println("Média: " + est.getAverage());
        System.out.println("Quantia: " + est.getSum());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());


    }

}
