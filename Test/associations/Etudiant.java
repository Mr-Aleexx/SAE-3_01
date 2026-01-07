public class Etudiant {
    private Professeur professeur;

    public Etudiant(Professeur professeur) {
        this.professeur = professeur;
    }

    public Professeur getProfesseur() {
        return professeur;
    }
}
