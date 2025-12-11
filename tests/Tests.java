public class Tests
{
	public    static final int    pub1  = 8;
	private   static final long   priv1 = 5;
	          static final Double pack1 = 15.5;
	protected static final String prot1 = "test";

	public    final int    pub2  = 8;
	private   final long   priv2 = 5;
	          final Double pack2 = 15.5;
	protected final String prot2 = "test";

	public    static int    pub3  = 8;
	private   static long   priv3 = 5;
	          static Double pack3 = 15.5;
	protected static String prot3 = "test";

	public    int    pub4  = 8;
	private   long   priv4 = 5;
	          Double pack4 = 15.5;
	protected String prot4 = "test";

	int testMethodeInit = this.initAttr();

	public int initAttr()
	{
		return 0;
	}
}
