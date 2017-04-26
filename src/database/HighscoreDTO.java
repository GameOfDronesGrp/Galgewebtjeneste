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
        private int userID;
	private String username;
        private String score;
	private String datetime;
	
	
	public HighscoreDTO() {
		this.userID = 0;
		this.username = null;
		this.score = null;
		this.datetime = null;
		
	}

	public HighscoreDTO(int userID, String username, String score, String datetime) {
		this.userID = userID;
		this.username = username;
		this.score = score;
		this.datetime = datetime;
		
	}
	
	public HighscoreDTO(HighscoreDTO hs)
    {
    	this.userID = hs.getUserID();
    	this.username = hs.getUserName();
    	this.score = hs.getHighscore();
    	this.datetime = hs.getDatetime();
    	
    }
	
	public HighscoreDTO(Object hs)
    {
    	this.userID = ((HighscoreDTO) hs).getUserID();
    	this.username = ((HighscoreDTO) hs).getUserName();
    	this.score = ((HighscoreDTO) hs).getHighscore();
    	this.datetime = ((HighscoreDTO) hs).getDatetime();
    	
    }

	
	public int getUserID() { return userID; }
	public void setUserID(int id) { this.userID = userID; }
	public String getUserName() { return username; }
	public void setUserName(String username) { this.username = username; }
	public String getHighscore() { return score; }
	public void setHighscore(String score) { this.score = score; }
	public String getDatetime() { return datetime; }
	public void setDatetime(String datetime) { this.datetime = datetime; }
	
	public String toString() { return userID + "\t" + username + "\t" + score + "\t" + datetime; }
}

