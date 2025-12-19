// ================================
// INTERFACE AVEC TOUTES LES SYNTAXES
// ================================

@FunctionalInterface // optionnel (oblige une seule méthode abstraite)
public interface InterfaceComplete extends Comparable, AutoCloseable {

    // ----------------
    // CONSTANTES
    // ----------------
    // public static final implicite
    int CONST_INT = 10;
    String CONST_STRING = "HELLO";
	String[][] TAB = { {"blabla", "test1" },
	                   {"bleble", "test2" },
	                   {"blublu", "test3" }};

    // ----------------
    // METHODES ABSTRAITES
    // ----------------
    // public abstract implicite
    void methodeAbstraite();

    // Avec paramètre et retour
    T calculer(T valeur);

    // ----------------
    // METHODE DEFAULT (Java 8+)
    // ----------------
    default void methodeParDefaut() {
        log("Méthode par défaut");
        methodePrivee();
    }

    // ----------------
    // METHODE STATIC
    // ----------------
    static void methodeStatique() {
        System.out.println("Méthode statique de l’interface");
    }

    // ----------------
    // METHODE PRIVATE (Java 9+)
    // ----------------
    private void methodePrivee() {
        System.out.println("Méthode privée de l’interface");
    }

    private static void log(String msg) {
        System.out.println("[LOG] " + msg);
    }

    // ----------------
    // TYPE IMBRIQUÉ
    // ----------------
    class ClasseInterne {
        public void afficher() {
            System.out.println("Classe interne dans une interface");
        }
    }

    interface InterfaceInterne {
        void run();
    }

    enum EnumInterne {
        A, B, C
    }
}
