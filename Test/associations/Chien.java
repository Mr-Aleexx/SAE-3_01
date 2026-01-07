public class Chien extends Animal {

    public Chien(String nom) {
        super(nom);
    }

    @Override
    public void parler() {
        System.out.println("Le chien aboie");
    }
}
