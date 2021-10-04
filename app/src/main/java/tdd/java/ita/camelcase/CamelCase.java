package tdd.java.ita.camelcase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CamelCase {
    public static List<String> converterCamelCase(String original) {
        List<String> palavras = new ArrayList<>();
        String palavraAtual = "";

        for(char letra : original.toCharArray()) {
            if(Character.isUpperCase(letra) && !eSigla(palavraAtual))
                palavras = adicionarPalavraNaoVaziaEmLista(palavraAtual, palavras);

            palavraAtual = incluirLetra(palavraAtual, letra, Character.isUpperCase(letra) && !eSigla(palavraAtual));
        };

        return adicionarPalavraNaoVaziaEmLista(palavraAtual, palavras);
    }

    private static List<String> adicionarPalavraNaoVaziaEmLista(String palavra, List<String> lista) {
        if(palavra != null && !palavra.isEmpty()) {
            if(eSigla(palavra))
                lista.add(palavra);
            else
                lista.add(palavra.toLowerCase());
        }

        return lista;
    }

    private static String incluirLetra(String palavra, char letra, boolean reiniciarPalavra) {
        if(reiniciarPalavra)
            return String.valueOf(letra);

        return palavra + letra;
    }

    private static boolean eSigla(String palavra) {
        return palavra.length() > 0 && palavra.chars().allMatch(caracter -> {
            return Character.isUpperCase(caracter);
        });
    }
}
