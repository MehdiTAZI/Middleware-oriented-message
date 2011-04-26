package fr.esiag.mezzodijava.mezzo.cosadmin;

/**
 * 
 *
 */
public class Manager 
{
    public static void main( String[] args )
    {
	
    }
    
    public static void printUsage(){
	System.out.println("Usage :");
	System.out.println(" -list");
	System.out.println("      List existing time and event servers");
	System.out.println(" -listchannel");
	System.out.println("      List existing channels");
	System.out.println(" -addchannel <server name> <channel name> <capacity>");
	System.out.println("      Add a channel");
	System.out.println(" -delchannel <server name> <channel name>");
	System.out.println("      Delete a channel");
	System.out.println(" -modchannel <server name> <capacity>");
	System.out.println("      Modify a channel");
	System.out.println(" -gui");
	System.out.println("      Launch GUI");
    }
}
