package tdd.java.ita.camelcase;

import org.junit.Test;

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
        String texto = "nomeComposto";

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
        assertEquals("CPF", listaPalavras.get(1));
    }
}
