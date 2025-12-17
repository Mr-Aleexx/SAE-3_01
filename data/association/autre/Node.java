// Classe Node
import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private List<Node> children;
	private Node mother;

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void showChildren() {
        System.out.println("Node " + name + " a pour enfants :");
        for (Node child : children) {
            System.out.println("- " + child.name);
        }
    }
}