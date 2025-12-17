// Classe C
public abstract class C {
    private D d;

    public abstract void setD(D d);

    public void showD() {
        System.out.println("C connaît D : " + d.getName());
    }
}