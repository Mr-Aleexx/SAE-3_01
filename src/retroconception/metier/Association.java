package retroconception.metier;

/**
 * Représentation d'une association 
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */

public class Association
{
	private Classe classe1;
	private Classe classe2;
	private String typeAsso;
	private String multiplicite1;
	private String multiplicite2;
	private String role1;
	private String role2;
	
	public Association( Classe classe1, Classe classe2, String typeAsso, String multiplicite1,
	                    String multiplicite2 )
	{
		this.classe1       = classe1;
		this.classe2       = classe2;
		this.typeAsso      = typeAsso;
		this.multiplicite1 = multiplicite1;
		this.multiplicite2 = multiplicite2;
		this.role1         = null; 
		this.role2         = null; 
	}

	public Association( Classe classe1, Classe classe2, String multiplicite1 )
	{
		this(classe1, classe2, null, multiplicite1, null);
	}

	public String getTypeAsso     () { return this.typeAsso;     }
	public Classe getClasse1      () { return this.classe1;      }
	public Classe getClasse2      () { return this.classe2;      }
	public String getMultiplicite () { return this.multiplicite1;}
	public String getMultiplicite1() { return this.multiplicite1;}
	public String getMultiplicite2() { return this.multiplicite2;}
	public String getRole1        () { return this.role1;        }
	public String getRole2        () { return this.role2;        }

	public void setTypeAsso( String typeAsso ) { this.typeAsso = typeAsso; }
	public void setClasse1 ( Classe classe1  ) { this.classe1  = classe1;  }
	public void setClasse2 ( Classe classe2  ) { this.classe2  = classe2;  }
	public void setRole1   ( String role1    ) { this.role1    = role1;    }
	public void setRole2   ( String role2    ) { this.role2    = role2;    }
}