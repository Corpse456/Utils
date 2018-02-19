package fileListings;

public class Main {

    public static void main(String[] args) {
	
	Catalogs catalogs = new Catalogs("g:", "d://Games.exe//One.csv");
	if (catalogs.createList()) System.out.println("Done");
    }
}
