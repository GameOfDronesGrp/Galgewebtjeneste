/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;

/**
 *
 * @author haydar
 */
public class Main {
    
    
    public static void main(String[] args) throws DALException, ClassNotFoundException, SQLException{
        
        Connector dbc = new Connector();
        HighscoreDTO hs = new HighscoreDTO("s154088");
        HighscoreDAO hsdao = new HighscoreDAO();
        
        //Prøver at hente data fra sql serveren...
        System.out.printf("\nBrugeren %s har score: %d\n", hs.getUserID(),
                hsdao.getScore(hs.getUserID()));
        
        
        
        System.out.println("Updaterer highscoren for brugeren til 200");
        hs.setScore(200);
        hsdao.updateHighscore(hs);
        
        System.out.printf("\nBrugeren %s har score: %d\n", hs.getUserID(),
                hsdao.getScore(hs.getUserID()));
        
    }
}