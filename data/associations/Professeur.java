import java.util.List;

public class Professeur {
    private List<Etudiant> etudiants;

    public Professeur(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }
}
