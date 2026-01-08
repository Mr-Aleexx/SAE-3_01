public class Commande {
    private Client client;

    public Commande(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
