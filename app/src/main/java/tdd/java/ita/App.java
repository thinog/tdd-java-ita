/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package tdd.java.ita;

import tdd.java.ita.camelcase.CamelCase;

import java.security.InvalidParameterException;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Faltando argumento.");
            return;
        }

        try {
            CamelCase.converterCamelCase(args[0]).stream().forEach(palavra -> System.out.println(palavra));
        } catch (InvalidParameterException ipe) {
            System.out.printf("Erro: %s", ipe.getMessage());
        }
    }
}
