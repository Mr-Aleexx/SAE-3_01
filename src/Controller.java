import metier.Parser;	

public class Controller
{


	public static void main(String[] args)
	{
		Parser p = new Parser("src/ressources/Point.java");
		System.out.println(p);
	}
}
