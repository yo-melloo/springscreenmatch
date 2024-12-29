package br.com.mello.springscreenmatch.principal;

import br.com.mello.springscreenmatch.model.DadosSerie;
import br.com.mello.springscreenmatch.model.DadosTemporada;
import br.com.mello.springscreenmatch.service.ConsumoApi;
import br.com.mello.springscreenmatch.service.ConverteDados;

import java.util.*;

public class Principal {
    private final String endereco = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=1a6bcfd8";
    private final ConsumoApi consultaAPI = new ConsumoApi();
    private final Scanner entrada = new Scanner(System.in);
    private final ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> listaSeries = new ArrayList<>();

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
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 0; i < dadosSerie.totalTemporadas(); i++) {
            var json = consultaAPI.realizarConsulta(endereco + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.jsonParaObjeto(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void listarSeries() {
        listaSeries.forEach(System.out::println);
    }

}
