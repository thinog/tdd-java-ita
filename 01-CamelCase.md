Neste relatório irei colocar, na respectiva ordem, os testes implementados e as alterações realizadas no código para poder passar no teste. No código alterado irei adicionar apenas métodos novos e alterados.

---

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
// criado
public static List<String> converterCamelCase(String original) {

}
```

Teste falhou.

Alteração no código de produção:
```java
// alterado
public static List<String> converterCamelCase(String original) {
    return new ArrayList<>(){{add(original);}};
}
```

Teste passou.

---

Segundo teste:
```java
@Test
public void testeCamelCasePalavraComposta() {
    String texto = "NomeComposto";

    List<String> listaPalavras = CamelCase.converterCamelCase(texto);

    assertEquals(2, listaPalavras.size());
    assertEquals("nome", listaPalavras.get(0));
    assertEquals("composto", listaPalavras.get(1));
}
```

Teste falhou.

Alteração no código de produção:
```java
// alterado
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

Teste passou.

Refatoração para regra de 10 linhas por método:
```java
// alterado
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

// criado
private static List<String> adicionarPalavraNaoVaziaEmLista(String palavra, List<String> lista) {
    if(palavra != null && !palavra.isEmpty())
        lista.add(palavra.toLowerCase());

    return lista;
}

// criado
private static String incluirLetra(String palavra, char letra, boolean reiniciarPalavra) {
    if(reiniciarPalavra)
        return String.valueOf(letra);

    return palavra + letra;
}
```

Teste passou.

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

Teste falhou.

Alteração em código de produção:
```java
// alterado
public static List<String> converterCamelCase(String original) {
    List<String> palavras = new ArrayList<>();
    String palavraAtual = "";

    for(char letra : original.toCharArray()) {
        if(Character.isUpperCase(letra) && !checaSigla(palavraAtual))
            palavras = adicionarPalavraNaoVaziaEmLista(palavraAtual, palavras);

        palavraAtual = incluirLetra(palavraAtual, letra, Character.isUpperCase(letra) && !checaSigla(palavraAtual));
    };

    return adicionarPalavraNaoVaziaEmLista(palavraAtual, palavras);
}

// alterado
private static List<String> adicionarPalavraNaoVaziaEmLista(String palavra, List<String> lista) {
    if(palavra != null && !palavra.isEmpty()) {
        if(checaSigla(palavra))
            lista.add(palavra);
        else
            lista.add(palavra.toLowerCase());
    }

    return lista;
}

// criado
private static boolean checaSigla(String palavra) {
    return palavra.length() > 0 && palavra.chars().allMatch(caracter -> {
        return Character.isUpperCase(caracter);
    });
}
```

Teste passou.

---

Quarto teste:
```java
@Test
public void testeCamelCasePalavraCompostaComSigla() {
    String texto = "numeroCPFContribuinte";

    List<String> listaPalavras = CamelCase.converterCamelCase(texto);

    assertEquals(3, listaPalavras.size());
    assertEquals("numero", listaPalavras.get(0));
    assertEquals("CPF", listaPalavras.get(1));
    assertEquals("contribuinte", listaPalavras.get(2));
}
```

Teste falhou.

Alteração em código de produção:
```java
// alterado
public static List<String> converterCamelCase(String original) {
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

// criado
private static boolean checaFinalPalavra(char letraAtual, char letraSeguinte, String palavra) {
    if(Character.isUpperCase(letraAtual) && !checaSigla(palavra))
        return true;

    if(Character.isUpperCase(letraAtual) && Character.isLowerCase(letraSeguinte))
        return true;

    return false;
}
```

Teste passou.

---

Quinto teste:
```java
@Test
public void testeCamelCasePalavraCompostaComNumero() {
    String texto = "recupera10Primeiros";

    List<String> listaPalavras = CamelCase.converterCamelCase(texto);

    assertEquals(3, listaPalavras.size());
    assertEquals("recupera", listaPalavras.get(0));
    assertEquals("10", listaPalavras.get(1));
    assertEquals("primeiros", listaPalavras.get(2));
}
```

Teste falhou.

Alteração em código de produção:
```java
// criado
private static boolean checaNumero(String palavra) {
    return palavra.length() > 0 && palavra.chars().allMatch(caracter -> {
        return Character.isDigit(caracter);
    });
}

// alterado
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
```

Teste passou.

---

Sexto teste:
```java
@Test(expected = InvalidParameterException.class)
public void testeErroCamelCasePalavraIniciadaComNumero() {
    String texto = "10Primeiros";
    CamelCase.converterCamelCase(texto);
}
```

Teste falhou.

Alteração em código de produção:
```java
// alterado
public static List<String> converterCamelCase(String original) {
    if(!checaPalavraValida(original))
        throw new InvalidParameterException("Palavra inválida!");

    ...
}

// criado
private static boolean checaPalavraValida(String palavra) {
    if(palavra == null || palavra.length() == 0)
        return false;

    char primeiraLetra = palavra.toCharArray()[0];
    if(!Character.isAlphabetic(primeiraLetra))
        return false;

    return true;
}
```

Teste passou.

---

Sétimo teste:
```java
@Test(expected = InvalidParameterException.class)
public void testeErroCamelCaseCaracterEspecial() {
    String texto = "nome#Composto";
    CamelCase.converterCamelCase(texto);
}
```

Teste falhou.


Alteração em código para passar teste:
```java
// alterado
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
```

Teste passou.

---

