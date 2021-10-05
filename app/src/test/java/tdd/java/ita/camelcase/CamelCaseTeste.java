package tdd.java.ita.camelcase;

import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CamelCaseTeste {
    @Test
    public void testeCamelCaseUmaPalavra() {
        String texto = "nome";

        List<String> listaPalavras = CamelCase.converterCamelCase(texto);

        assertEquals(1, listaPalavras.size());
        assertEquals("nome", listaPalavras.get(0));
    }

    @Test
    public void testeCamelCasePalavraComposta() {
        String texto = "NomeComposto";

        List<String> listaPalavras = CamelCase.converterCamelCase(texto);

        assertEquals(2, listaPalavras.size());
        assertEquals("nome", listaPalavras.get(0));
        assertEquals("composto", listaPalavras.get(1));
    }

    @Test
    public void testeCamelCaseUmaSigla() {
        String texto = "CPF";

        List<String> listaPalavras = CamelCase.converterCamelCase(texto);

        assertEquals(1, listaPalavras.size());
        assertEquals("CPF", listaPalavras.get(0));
    }

    @Test
    public void testeCamelCasePalavraCompostaComSigla() {
        String texto = "numeroCPFContribuinte";

        List<String> listaPalavras = CamelCase.converterCamelCase(texto);

        assertEquals(3, listaPalavras.size());
        assertEquals("numero", listaPalavras.get(0));
        assertEquals("CPF", listaPalavras.get(1));
        assertEquals("contribuinte", listaPalavras.get(2));
    }

    @Test
    public void testeCamelCasePalavraCompostaComNumero() {
        String texto = "recupera10Primeiros";

        List<String> listaPalavras = CamelCase.converterCamelCase(texto);

        assertEquals(3, listaPalavras.size());
        assertEquals("recupera", listaPalavras.get(0));
        assertEquals("10", listaPalavras.get(1));
        assertEquals("primeiros", listaPalavras.get(2));
    }

    @Test(expected = InvalidParameterException.class)
    public void testeErroCamelCasePalavraIniciadaComNumero() {
        String texto = "10Primeiros";
        CamelCase.converterCamelCase(texto);
    }

    @Test(expected = InvalidParameterException.class)
    public void testeErroCamelCaseCaracterEspecial() {
        String texto = "nome#Composto";
        CamelCase.converterCamelCase(texto);
    }
}
