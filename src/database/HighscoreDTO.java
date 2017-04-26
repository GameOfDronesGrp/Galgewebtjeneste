/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
/**
 *
 * @author haydar
 */
public class HighscoreDTO implements Serializable {
    private static final long serialVersionUID = 1L;
        private String userID;
        private int score;
	private String time;
        private int rank;
	
	
	public HighscoreDTO(String user_id) {
            this.userID = user_id;
            this.score = 0;
            this.time = "NOW()";
	}

	public HighscoreDTO(String userID, int score, String time) {
            this.userID = userID;
            this.score = score;
            this.time = time;
		
	}
	public HighscoreDTO(int rank, String userID, int score, String time) {
            this.rank = rank;
            this.userID = userID;
            this.score = score;
            this.time = time;
		
	}
	public HighscoreDTO(HighscoreDTO hs){
            this.userID = hs.getUserID();
            this.score = hs.getScore();
            this.time = hs.getDatetime();
    	
        }
	
	public HighscoreDTO(Object hs){
            this.userID = ((HighscoreDTO) hs).getUserID();
            this.score = ((HighscoreDTO) hs).getScore();
            this.time = ((HighscoreDTO) hs).getDatetime();
    	}

	public int getUserRank(String userID){
            if(rank > 0){
                return 1;
            }else{
                return -1;
            }
        }
        public String getUserID() { return userID; }
	public void setUserID(int id) { this.userID = userID; }
	public int getScore() { return score; }
	public void setScore(int score) { this.score = score; }
	public String getDatetime() { return time; }
	public void setDatetime(String datetime) { this.time = datetime; }
	public String toString() { return userID + "\t" + score + "\t" + time; }
}

