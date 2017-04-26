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


public class HighscoreDAO {
        
        public HighscoreDAO(){
        }
        
        //@Override
        //public boolean hentBruger(String brugernavn, String password) throws RemoteException {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //}
    

        public HighscoreDTO getHighscore(String userID) throws DALException, ClassNotFoundException {
            Connector connector = new Connector();
            try {
                ResultSet rs = connector.doQuery("SELECT * FROM Highscore WHERE user_id = " + userID+"';");
                if(rs.next())
                    return new HighscoreDTO(rs.getString("user_id"), rs.getInt("score"), rs.getString("time"));
                else System.out.println("Ingen værdier fundet...");
            } catch (SQLException ex) {
                System.out.println("Kunne ikke hente highscore");
                Logger.getLogger(HighscoreDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
           return null;
        }
        
        public int getScore(String userID) throws DALException, ClassNotFoundException{
            Connector connector = new Connector();
            try {
                ResultSet rs = connector.doQuery("SELECT score FROM Highscore WHERE user_id = \""+ userID+"\";");
                int score = 0;
                if(rs.next()) score = rs.getInt("score");
                else System.out.println("Ingen værdier fundet...");
                return score;
            } catch (SQLException ex) {
                Logger.getLogger(HighscoreDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Kunne ikke hente score");
            }
          return -1;  
        }
        
        
        //@Override
	public void createHighscore(HighscoreDTO hs) throws DALException, ClassNotFoundException {
            Connector connector = new Connector();
            try {
                connector.doUpdate(
                        "INSERT INTO user(user_id, score) VALUES " +
                        "('" + hs.getUserID()+ "', " + hs.getScore() + ");");
                    } catch (SQLException exception) {
                        Logger.getLogger(HighscoreDAO.class.getName()).log(Level.SEVERE, null, exception);
            }
	}
        //@Override
	public void updateHighscore(HighscoreDTO hs) throws DALException, ClassNotFoundException {
            Connector connector = new Connector();
            try {
                connector.doUpdate( "UPDATE Highscore SET  score = " + hs.getScore() + 
                        ", time =  NOW() WHERE user_id = '"+hs.getUserID()+"';");
            } catch (SQLException e) {
                throw new DALException(e);
            }
	}

	public List<HighscoreDTO> getHighscoreList() throws DALException, ClassNotFoundException {
            Connector connector = new Connector();
            List<HighscoreDTO> list = new ArrayList<HighscoreDTO>();
            try {
                ResultSet rs = connector.doQuery("SELECT  @curRank := @curRank + 1  AS rank, user_id,"+
                        " score, time  FROM ( SELECT @curRank := 0) r,  Highscore h  ORDER BY  score DESC;");
                while (rs.next()){
                    list.add(new HighscoreDTO(rs.getInt("rank"), rs.getString("user_id"), rs.getInt("score"), rs.getString("time")));
                }
            }catch (SQLException e){
                throw new DALException(e);
            }
            return list;
        }
}
