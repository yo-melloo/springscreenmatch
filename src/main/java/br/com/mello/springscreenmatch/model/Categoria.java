package br.com.mello.springscreenmatch.model;

public enum Categoria {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String categoriaTexto){
        for(Categoria c : Categoria.values()){
            if (c.categoriaOmdb.equalsIgnoreCase(categoriaTexto)){
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada.");
    }

}










