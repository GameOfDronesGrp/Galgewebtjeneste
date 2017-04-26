
package galgeleg;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;



public class Client {
    
    //main run
    public static void main(String[] args) throws MalformedURLException, RemoteException{
        run();
    }
    
    //menuerne
    private static void run() throws MalformedURLException, RemoteException {
        
        URL url = new URL("http://130.226.195.227:9591/galgelegtjeneste?wsdl");
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        Service service = Service.create(url, qname);
        GalgelegI game = service.getPort(GalgelegI.class);
        
        Scanner scan = new Scanner(System.in);
        boolean loggedIn = false;
        int choice;
        
        while(true){
            if(loggedIn == false){
                
                System.out.println("1. Log ind");
                System.out.println("2. Afslut");
                
                choice = scan.nextInt();
                switch(choice){
                    case 1: 
                        String bruger;
                        String kode;
                        System.out.println("Skriv login");
                        bruger = scan.next();
                        System.out.println("Skriv kode");
                        kode = scan.next();
                        if(game.hentBruger(bruger, kode) == true){
                            loggedIn = true;
                        }else{
                            loggedIn = false;
                        } break;
                    case 2: break;
                    default: break;
                }
            }else{
                System.out.println("1. Nyt spil");
                System.out.println("2. Log ud");
                
                choice = scan.nextInt();
                switch(choice){
                    case 1: spil(); ; break;
                    case 2: loggedIn = false; break;
                    default: break;
                }
            }
        }
    }
    
    private static void spil() throws MalformedURLException {
        URL url = new URL("http://130.226.195.227:9591/galgelegtjeneste?wsdl");
        QName qname = new QName("http://galgeleg/", "GalgelogikService");
        Service service = Service.create(url, qname);
        GalgelegI game = service.getPort(GalgelegI.class);
        
        Scanner scan = new Scanner(System.in);
        String gaet;
        int liv = 7;
        
        System.out.println("- Spillet er startet -");
        
        while(!game.erSpilletSlut()){
            System.out.println("Dit ord "+ game.getSynligtOrd());
            System.out.println("Dine liv " + liv);
            System.out.println("Gæt på et bogstav");
            
            gaet= scan.nextLine();
            game.gætBogstav(gaet);
            if (!game.getOrdet().contains(gaet)) {
                System.out.println("Du gættede forkert!");
                liv--;
            }else{
                System.out.println("Du gættede rigtigt");
            }
            
            if(game.erSpilletTabt()){
                System.out.println("Du har tabt, Ordet var: " + game.getOrdet());
            }else if(game.erSpilletVundet()){
                System.out.println("Du har vundet");
            }
        }
        game.nulstil();  
    }
    
}
