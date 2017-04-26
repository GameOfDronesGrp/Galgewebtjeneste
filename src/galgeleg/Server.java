/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package galgeleg;

import javax.xml.ws.Endpoint;


public class Server {
		public static void main(String[] args) {
		System.out.println("publicerer Galgelegtjeneste");
		GalgelegI g = new Galgelogik();
    // Ipv6-addressen [::] svarer til Ipv4-adressen 0.0.0.0, der matcher alle maskinens netkort og 
		Endpoint.publish("http://[::]:9591/galgelegtjeneste", g);
		System.out.println("Galgelegtjeneste publiceret.");
	}
}
