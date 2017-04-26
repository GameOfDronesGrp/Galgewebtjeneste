/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.List;
import javax.jws.WebMethod;

/**
 *
 * 
 */
public interface HighscoreI {
    
	HighscoreDTO getUser(int userID)throws DALException; 
        List<HighscoreDTO> getHighscoreList()throws DALException;	
        void createHighscore(HighscoreDTO hs)throws DALException;	
        void updateHighscore(HighscoreDTO hs)throws DALException;
        @WebMethod public boolean hentBruger(String brugernavn, String password) throws java.rmi.RemoteException;;

} 
    

