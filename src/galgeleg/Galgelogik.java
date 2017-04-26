package galgeleg;

import brugerautorisation.transport.soap.Brugeradmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

@WebService(endpointInterface = "galgeleg.GalgelegI")
public class Galgelogik implements GalgelegI {
  private ArrayList<String> muligeOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd;
  private int antalForkerteBogstaver;
  private boolean sidsteBogstavVarKorrekt;
  private boolean spilletErVundet;
  private boolean spilletErTabt;
  private String loggedInBruger;

  @Override
  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }

  @Override
  public String getSynligtOrd() {
    return synligtOrd;
  }

  @Override
  public String getOrdet() {
    return ordet;
  }

  @Override
  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }

  @Override
  public boolean erSidsteBogstavKorrekt() {
    return sidsteBogstavVarKorrekt;
  }

  @Override
  public boolean erSpilletVundet() {
    return spilletErVundet;
  }

  @Override
  public boolean erSpilletTabt() {
    return spilletErTabt;
  }

  @Override
  public boolean erSpilletSlut() {
      if(erSpilletVundet() == true){
          // loggedInBruger skal bruges til at indsætte værdier om hvem der spiller (DATABASEN).
          // Husk de point der allerede er inde i databasen.
          int point = (250 + ((getBrugteBogstaver().size() - getAntalForkerteBogstaver())*8));
      } else if (erSpilletTabt() == true) {
          int point = ((getBrugteBogstaver().size() - getAntalForkerteBogstaver())*8);
      }
    return spilletErTabt || spilletErVundet;
  }

  public Galgelogik() {
    muligeOrd.add("bil");
    muligeOrd.add("computer");
    muligeOrd.add("programmering");
    muligeOrd.add("motorvej");
    muligeOrd.add("busrute");
    muligeOrd.add("gangsti");
    muligeOrd.add("skovsnegl");
    muligeOrd.add("solsort");
    nulstil();
  }

  @Override
  public void nulstil() {
    brugteBogstaver.clear();
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
    opdaterSynligtOrd();
  }

  @Override
  public void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }

  @Override
  public void gætBogstav(String bogstav) {
    if (bogstav.length() != 1) return;
    System.out.println("Der gættes på bogstavet: " + bogstav);
    if (brugteBogstaver.contains(bogstav)) return;
    if (spilletErVundet || spilletErTabt) return;

    brugteBogstaver.add(bogstav);

    if (ordet.contains(bogstav)) {
      sidsteBogstavVarKorrekt = true;
      System.out.println("Bogstavet var korrekt: " + bogstav);
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      sidsteBogstavVarKorrekt = false;
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
      antalForkerteBogstaver = antalForkerteBogstaver + 1;
      if (antalForkerteBogstaver > 6) {
        spilletErTabt = true;
      }
    }
    opdaterSynligtOrd();
  }

  public void logStatus() {
    System.out.println("---------- ");
    System.out.println("- ordet (skult) = " + ordet);
    System.out.println("- synligtOrd = " + synligtOrd);
    System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
    System.out.println("- brugeBogstaver = " + brugteBogstaver);
    if (spilletErTabt) System.out.println("- SPILLET ER TABT");
    if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
  }


  public static String hentUrl(String url) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }

  public void hentOrdFraDr() throws Exception {
    String data = hentUrl("http://dr.dk");
    data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));
    nulstil();
  }
  
  	public boolean hentBruger(String name, String pass) throws RemoteException {
		Brugeradmin ba;
		try {
		URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
		QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
		Service service = Service.create(url, qname);
		ba = service.getPort(Brugeradmin.class);
                
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return false;
		}
		try{
			ba.hentBruger(name, pass);
                        hentOrdFraDr();
                        loggedInBruger = name;
			return true;
			
		} catch(IllegalArgumentException e){
			e.printStackTrace();
		} catch (Exception ex) {
          Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
      }
		return false;
	}
        
                
}
