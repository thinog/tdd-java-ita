package tdd.java.ita.camelcase;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class CamelCase {
    public static List<String> converterCamelCase(String original) {
        if(!checaPalavraValida(original))
            throw new InvalidParameterException("Palavra inválida!");

        List<String> palavras = new ArrayList<>();
        String palavraAtual = "";

        for(int index = 0; index < original.length(); index++) {
            char letraAtual = original.toCharArray()[index];
            char letraSeguinte = (index + 1) < original.length() ? original.toCharArray()[index + 1] : Character.MIN_VALUE;
            boolean eFinalPalavra = checaFinalPalavra(letraAtual, letraSeguinte, palavraAtual);

            if(eFinalPalavra)
                palavras = adicionarPalavraNaoVaziaEmLista(palavraAtual, palavras);

            palavraAtual = incluirLetra(palavraAtual, letraAtual, eFinalPalavra);
        };

        return adicionarPalavraNaoVaziaEmLista(palavraAtual, palavras);
    }

    private static List<String> adicionarPalavraNaoVaziaEmLista(String palavra, List<String> lista) {
        if(palavra != null && !palavra.isEmpty()) {
            if(checaSigla(palavra))
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

    private static boolean checaSigla(String palavra) {
        return palavra.length() > 0 && palavra.chars().allMatch(caracter -> {
            return Character.isUpperCase(caracter);
        });
    }

    private static boolean checaNumero(String palavra) {
        return palavra.length() > 0 && palavra.chars().allMatch(caracter -> {
            return Character.isDigit(caracter);
        });
    }

    private static boolean checaFinalPalavra(char letraAtual, char letraSeguinte, String palavra) {
        if(Character.isUpperCase(letraAtual) && !checaSigla(palavra))
            return true;
        if(Character.isUpperCase(letraAtual) && Character.isLowerCase(letraSeguinte))
            return true;
        if(Character.isDigit(letraAtual) && !checaNumero(palavra))
            return true;
        if(checaNumero(palavra) && !Character.isDigit(letraAtual))
            return true;

        return false;
    }

    private static boolean checaPalavraValida(String palavra) {
        if(palavra == null || palavra.length() == 0)
            return false;

        char primeiraLetra = palavra.toCharArray()[0];
        if(!Character.isAlphabetic(primeiraLetra))
            return false;

        boolean possuiCaracterInvalido = palavra.chars().allMatch(caracter -> {
            return Character.isDigit(caracter) || Character.isAlphabetic(caracter);
        });
        if(!possuiCaracterInvalido)
            return false;

        return true;
    }
}
