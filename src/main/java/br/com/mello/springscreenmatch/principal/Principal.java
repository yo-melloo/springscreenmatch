package br.com.mello.springscreenmatch.principal;

import br.com.mello.springscreenmatch.model.DadosSerie;
import br.com.mello.springscreenmatch.model.DadosTemporada;
import br.com.mello.springscreenmatch.model.Episodio;
import br.com.mello.springscreenmatch.model.Serie;
import br.com.mello.springscreenmatch.repository.SerieRepository;
import br.com.mello.springscreenmatch.service.ConsumoApi;
import br.com.mello.springscreenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String endereco = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("OMDB_APIKEY");
    private final ConsumoApi consultaAPI = new ConsumoApi();
    private final Scanner entrada = new Scanner(System.in);
    private final ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> listaSeries = new ArrayList<>();
    private Scanner leitura = new Scanner(System.in);

    private SerieRepository repositorio;
    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibirMenu(){
        var opcao = -1;

        var menu = """
                1. Pesquisar Séries
                2. Pesquisar episódios
                3. Listar séries pesquisadas
               \s
                Digite a opção selecionada:\s""";


        while (opcao != 0) {
            System.out.print(menu);
            opcao = entrada.nextInt();
            entrada.nextLine();

            //Analisando a opção escolhida:
            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeries();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        //Pesquisa e exibe os dados gerais da Série:
        DadosSerie dados = getDadosSerie();
        System.out.println(dados);
        Serie serie = new Serie(dados);
        repositorio.save(serie);
    }

    private DadosSerie getDadosSerie() {
        //Pesquisa os dados da Série:
        System.out.print("Digite o nome da série: ");
        var nomeSerie = entrada.nextLine();
        var json = consultaAPI.realizarConsulta(endereco + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie serie = conversor.jsonParaObjeto(json, DadosSerie.class);
        listaSeries.add(serie);
        return serie;
    }

    private void buscarEpisodioPorSerie() {
        listarSeries();
        System.out.println("Escolha uma série pelo nome");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i < serieEncontrada.getTotalTemporadas(); i++) {
                var json = consultaAPI.realizarConsulta(endereco + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.jsonParaObjeto(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
    }

    private void listarSeries() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }

}
