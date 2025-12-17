package com.example.test;

public interface TestInterface {

    // -----------------
    // Constantes
    // -----------------
    int MAX = 100;               // public static final implicite
    String NAME = "TestInterface";

    // -----------------
    // Méthodes abstraites
    // -----------------
    void doSomething();
    int compute(int a, int b);

    // -----------------
    // Méthode default
    // -----------------
    default void printName() {
        System.out.println("Interface name: " + NAME);
    }

    // -----------------
    // Méthode static
    // -----------------
    static void printMax() {
        System.out.println("Max value: " + MAX);
    }

    // -----------------
    // Méthodes privées (Java 9+)
    // -----------------
    private void helper() {
        System.out.println("This is a private instance method.");
    }

    private static void helperStatic() {
        System.out.println("This is a private static method.");
    }

    // -----------------
    // Exemple d'utilisation des private dans default
    // -----------------
    default void useHelper() {
        helper();
        helperStatic();
    }
}
