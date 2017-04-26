/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

public abstract class Constant
{
	public static final String
		server					= "localhost",  // database-serveren
		database				=  "Projekt",  //"jdbcdatabase", // navnet paa din database = dit studienummer
		username				= "root", // dit brugernavn = dit studienummer 
		password				= ""; // dit password som du har valgt til din database
	
	public static final int
		port					= 3306;
}
