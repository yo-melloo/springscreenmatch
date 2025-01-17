package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {
    @Autowired
    SerieService servico;

    @GetMapping("/")
    public String retornarAoInicio(){
        return "Bem-vindo";
    }

    @GetMapping("/series")
    public List<SerieDTO> obterSeries(){
        return servico.obterTodasAsSeries();
    }

}
