public class A {
    private B b;

    public A(B b) {
        this.b = b;
    }

    public void showB() {
        System.out.println("A connaît B : " + b.getName());
    }
}