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

    public void exibirMenu(){
        var menu = """
                1. Pesquisar Séries
                2. Pesquisar episódios
                
                Digite a opção selecionada:""";

        System.out.print(menu);
        var opcao = entrada.nextInt();
        entrada.nextLine();

        //Analisando a opção escolhida:
        switch (opcao){
            case 1:
                buscarSerieWeb();
                break;
            case 2:
                buscarEpisodioPorSerie();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida");
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
        return conversor.jsonParaObjeto(json, DadosSerie.class);
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

}
