package br.com.mello.springscreenmatch.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
