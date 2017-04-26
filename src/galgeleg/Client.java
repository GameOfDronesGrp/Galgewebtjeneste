package galgeleg;

import java.io.Console;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Client {
    
    //main run
    public static void main(String[] args) throws MalformedURLException, RemoteException, Exception{
        Scanner scan = new Scanner(System.in);
        //URL jakobs = new URL("http://130.226.195.227:9591/galgelegtjeneste?wsdl");   // Jacobs server
        URL local = new URL("http://localhost:9591/galgelegtjeneste?wsdl"); 
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        Service service = Service.create(local, qname);
        GalgelegI game = service.getPort(GalgelegI.class);
        run(game, scan);
        scan.close();
    }
    
    //menuerne
    private static void run(GalgelegI game, Scanner scan) throws RemoteException{
        
        boolean loggedIn = false;
        int choice;
        while(true){
            
            if(!loggedIn){
                System.out.println("1. Log ind");
                System.out.println("2. Afslut");
                try{
                    choice = (int)Integer.parseInt(scan.nextLine());
                }catch(NumberFormatException e){
                    System.out.println("Input skal være tal\n");
                    System.out.println();
                    choice = 0;
                }
                if(choice == 1){
                        String bruger;
                        String kode;
                        
                        try{
                            Console console = System.console();
                            bruger = console.readLine("Username: ");
                            char[] password = console.readPassword("Password: ");
                            kode = new String(password);
                        }catch(Exception e){
                            System.out.println("Skriv brugernavn");
                            bruger = scan.nextLine();
                            System.out.println("Skriv kode");
                            kode = scan.nextLine();
                        }
                        
                        if(game.hentBruger(bruger, kode)){
                            loggedIn = true;
                            System.out.printf("Hej %s. Du er nu logget ind!\n\n", bruger);
                        }else{
                            loggedIn = false;
                            System.out.println("\nBrugerinformation var forkert! Prøv igen\n\n");
                        }
                }else if(choice == 2){
                    System.out.println("Programmet lukker ned...");
                    break;
                }else {
                    System.out.println("Skriv 1 eller 2");
                }
                
            }else{
                System.out.println("1. Nyt spil");
                System.out.println("2. Log ud");
                
                try{
                    choice = (int) Integer.parseInt(scan.nextLine());
                }catch(NumberFormatException e){
                    System.out.println("Input skal være tal");
                    choice = 0;
                }
                
                switch(choice){
                    case 1: spil(game, scan); ; break;
                    case 2: {
                        System.out.println("Du er nu logget ud");
                        loggedIn = false;
                        break;
                    }
                    default: System.out.println("Skriv 1 eller 2");
                }
            }
        }
    }
    
    private static void spil(GalgelegI game, Scanner scan) {
        
        String gaet;
        final int liv = 7;
        game.nulstil();
        
        System.out.println("\n\n- Spillet er startet -");
        
        while(!game.erSpilletSlut()){
            scan.reset();
            System.out.println("Dit ord "+ game.getSynligtOrd());
            System.out.println("Brugte tegn: "+game.getBrugteBogstaver().toString());
            System.out.println("Dine liv " + (liv-game.getAntalForkerteBogstaver()));
            System.out.println("Gæt på et bogstav");
            gaet= scan.nextLine();
            
            try{
                if(Integer.parseInt(gaet) > -1){
                    System.out.println("Ingen cifrer tak :)\n");
                }
            }catch(NumberFormatException e){
                if(game.getBrugteBogstaver().contains(gaet)){
                    System.out.println("Du har allerede gættet på "+gaet+"\n");
                }else if(gaet.length()>1 || gaet.length() == 0){
                    System.out.println("Hov... der kom vist lidt for mange tegn der! \nDu må kun indtaste ét tegn.\n");
                }else{
                    game.gætBogstav(gaet);
                    if (!game.getOrdet().contains(gaet)) {
                        System.out.println("Du gættede forkert!\n");
                    }else{
                        System.out.println("Du gættede rigtigt\n");
                    }
                }
                if(game.erSpilletTabt()){
                    System.out.println("Du har tabt, Ordet var: " + game.getOrdet());
                }else if(game.erSpilletVundet()){
                    System.out.println("Du har vundet! Ordet var: "+game.getOrdet());
                }
            }
        }
    }
}