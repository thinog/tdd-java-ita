Primeiro teste implementado:
```java
@Test
public void testeCamelCaseUmaPalavra() {
    String texto = "nome";

    List<String> listaPalavras = CamelCase.converterCamelCase(texto);

    assertEquals(1, listaPalavras.size());
    assertEquals("nome", listaPalavras.get(0));
}
```

Estado inicial do método testado:
```java
public static List<String> converterCamelCase(String original) {

}
```

Teste falhou.

Alteração em código para passar teste:
```java
public static List<String> converterCamelCase(String original) {
    return new ArrayList<>(){{add(original);}};
}
```

---

Segundo teste:
```java
@Test
public void testeCamelCasePalavraComposta() {
    String texto = "nomeComposto";

    List<String> listaPalavras = CamelCase.converterCamelCase(texto);

    assertEquals(2, listaPalavras.size());
    assertEquals("nome", listaPalavras.get(0));
    assertEquals("composto", listaPalavras.get(1));
}
```

Teste falhou.

Alteração em código para passar teste:
```java
public static List<String> converterCamelCase(String original) {
    ArrayList<String> palavras = new ArrayList<>();
    String palavraAtual = "";

    for(char letra : original.toCharArray()) {
        if(Character.isUpperCase(letra) && !palavraAtual.isEmpty()) {
            palavras.add(palavraAtual.toLowerCase());
            palavraAtual = "";
        }

        palavraAtual += letra;
    };

    if(!palavraAtual.isEmpty())
        palavras.add(palavraAtual.toLowerCase());

    return palavras;
}
```

Refatoração para regra de 10 linhas por método:
```java
public static List<String> converterCamelCase(String original) {
    List<String> palavras = new ArrayList<>();
    String palavraAtual = "";

    for(char letra : original.toCharArray()) {
        if(Character.isUpperCase(letra))
            palavras = adicionarPalavraNaoVaziaEmLista(palavraAtual, palavras);

        palavraAtual = incluirLetra(palavraAtual, letra, Character.isUpperCase(letra));
    };

    return adicionarPalavraNaoVaziaEmLista(palavraAtual, palavras);
}

private static List<String> adicionarPalavraNaoVaziaEmLista(String palavra, List<String> lista) {
    if(palavra != null && !palavra.isEmpty())
        lista.add(palavra.toLowerCase());

    return lista;
}

private static String incluirLetra(String palavra, char letra, boolean reiniciarPalavra) {
    if(reiniciarPalavra)
        return String.valueOf(letra);

    return palavra + letra;
}
```

---

Terceiro teste:
```java
@Test
public void testeCamelCaseUmaSigla() {
    String texto = "CPF";

    List<String> listaPalavras = CamelCase.converterCamelCase(texto);

    assertEquals(1, listaPalavras.size());
    assertEquals("CPF", listaPalavras.get(0));
}
```

Alteração em código para passar teste:
```java
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
```

---

Terceiro teste:
```java

```