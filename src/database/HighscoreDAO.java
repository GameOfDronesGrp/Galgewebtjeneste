/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author haydar
 */
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.applet.Main;


public abstract class HighscoreDAO implements HighscoreI {
    
    

    @Override
    public boolean hentBruger(String brugernavn, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    public HighscoreDTO getHighscore(int userID) throws DALException, ClassNotFoundException {
		Connector connector = new Connector();

		try {
			ResultSet rs = connector.doQuery("SELECT * FROM user, Highscore WHERE user_id = " + userID);
			
                        return new HighscoreDTO(rs.getInt("user_id"), rs.getString("username"), rs.getString("score"), rs.getString("datetime"));
		}
		catch (SQLException e) {throw new DALException(e); }

	}
        
        @Override
	public void createHighscore(HighscoreDTO hs) throws DALException {		
		try {		
                    Connector connector;
                    
                    connector = new Connector();
                    Object ex = null;
                    
                    
                    
                    try {
                        
                      
                        connector.doUpdate(
                                "INSERT INTO user(user_id, username) VALUES " +
                                        "(" + hs.getUserID()+ ", '" + hs.getUserName() + "')"
                        );
                        connector.doUpdate(
                                "INSERT INTO Highscore(score, datetime) VALUES " +
                                        "('" + hs.getHighscore()+ "', '" + hs.getDatetime() + "')"
                        );
                    } catch (SQLException exception) {
                        Logger.getLogger(HighscoreDAO.class.getName()).log(Level.SEVERE, null, exception);
                    }
                } catch (ClassNotFoundException ex) {

			
		}
	}
        @Override
	public void updateHighscore(HighscoreDTO hs) throws DALException {
		try {
                    Connector connector = new Connector();
                    try {
                        connector.doUpdate(
                                "UPDATE Highscore SET  score = " + hs.getHighscore() + ", datetime =  '" + hs.getDatetime() +
                                        "'" 
                        );
                    } catch (SQLException e) {
                        
                        throw new DALException(e);
                    }
                } catch (ClassNotFoundException ex) {

			
		}
	}

	

	public List<HighscoreDTO> getHighscoreList() throws DALException {

		try {
                    
                    List<HighscoreDTO> list = new ArrayList<HighscoreDTO>();
                    
                    Connector connector = new Connector();
                    try {
                        ResultSet rs = connector.doQuery("SELECT * FROM user natural join Highscore");
                        
                        while (rs.next())
                        {
                            list.add(new HighscoreDTO(rs.getInt("user_id"), rs.getString("username"), rs.getString("score"), rs.getString("datetime")));
                        }
                    }
                    catch (SQLException e) { throw new DALException(e); }
                    return list;
                }
		catch (ClassNotFoundException ex) {
}
        return getHighscoreList();
	}

}
