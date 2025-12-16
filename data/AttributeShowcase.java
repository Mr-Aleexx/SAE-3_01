import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public class AttributeShowcase {

    // ----- 1. Attributs simples (tous types primitifs) -----
    public int publicInt;
    protected double protectedDouble;
    private boolean privateBoolean;
    byte packagePrivateByte; // pas de modificateur = package-private
    private char letter = 'A';
    public long BIGNUMBER_BIGNUMBER = 999999999L;
	
	{ // bloc d'initialisation d'instance
		x = 10;
		s = "hello";
		System.out.println("Bloc instance");
	}
    private short smallNumber = 12;

    // ----- 2. Types objets -----
    private String name;
    protected Integer wrappedInt = 42;
    public Object genericObject;

    // ----- 3. Tableaux -----
    private int[] numbers;
    public String[][] matrix;
    protected List<String>[] arrayOfLists;   // tableau générique (edge case)

    // ----- 4. Types génériques -----
    private   List<String> list;
    public    Map<String, Integer> scoreMap;
    protected Set<Double> scoreSet = new HashSet<>();
    private   Map<String,List<Integer>> nestedGenerics;
	private   Map<String,String> listMap;
    public    Optional<String> optValue = Optional.empty();
    public    Hashmap<String,HashMap<int,int>> hash;

    // ----- 5. static, final, static final (constantes) -----
    public static int staticCounter;
    private static final String               CONST_NAME="TEST";
    protected final int instanceId = 123;
    static final double PI = 3.14159;

    // ----- 6. volatile et transient -----
    private volatile boolean running;
    private transient int cachedValue;

    // ----- 7. Attributs avec annotations -----
    @NotNull
    private String annotatedField = "hello";

    @JsonProperty("identifier")
    private int id;

    // ----- 8. Modificateurs combinés -----
    public static volatile int staticVolatileInt;
    private final transient String finalTransient = "X";

    // ----- 9. Initialisations dans blocs -----
    private int initBlockValue;
    {
        initBlockValue = 5;
    }

    private static int staticInitBlockValue;
    static {
        staticInitBlockValue = 100;
    }

    // ----- 10. Attributs non initialisés (à compléter via constructeur) -----
    private double valueToSetLater;
    private final int finalSetInConstructor;

    // ----- 11. Constructeur -----
    public AttributeShowcase(int finalValue) {
        this.finalSetInConstructor = finalValue;
        this.list = new ArrayList<>();
        this.scoreMap = new HashMap<>();
        this.nestedGenerics = new HashMap<>();
    }

    public int test( Hashmap<String, HashMap<int, int>> a )
    {

    }

	public static int fooHash1(int a, int b, Hashmap  <  String alpha , HashMap  <  int a ,   int b >  > hash) {
		int capitalCities = 0;

		return capitalCities;
	}

    public static int fooHash2(int a,
    int b,
    int c,
    int d, 
    int e) {
		int capitalCities = 0;

		return capitalCities;
	}
}
