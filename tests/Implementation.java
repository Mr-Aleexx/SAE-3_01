import java.util.ArrayList;

public interface Implementation
{
    String alpha = "lapha";

    public boolean beta  = true;
    static String  gamma = "gamatoto";
    final float    delta = 5.5f;

    static boolean ahBon()
    {
        return true;
    }

    default void jouer(ArrayList<String> delta)
    {
        delta = new ArrayList<String>();
    }

    static final record nom (String hg){}

    //public interface InterInterne
    //{
    //    int codeInterne = 454554;
    //}
//
}