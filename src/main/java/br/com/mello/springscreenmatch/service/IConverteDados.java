package br.com.mello.springscreenmatch.service;

public interface IConverteDados {

    //recebe um tipo genérico (não definido), e converte em um objeto
    <T> T jsonParaObjeto(String json, Class<T> classe);
}
