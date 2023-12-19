package it.tomcatprogetto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.tomcatprogetto.connection.DbHandler;



/**
 * Servlet implementation class Registra
 */
@WebServlet("/Registra")
public class Registra extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registra() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
			String nome = request.getParameter("nome");
	        String cognome = request.getParameter("cognome");
	        String sesso = request.getParameter("sesso");
	        String luogoNascita = request.getParameter("luogoNascita");
	        String provincia = request.getParameter("provincia");
	        String dataNascita = request.getParameter("dataNascita");
	        String codiceFiscale = request.getParameter("codiceFiscale");
	        String password = request.getParameter("password");
	        String social = request.getParameter("trovato");
	      
	        DbHandler dbHandler = DbHandler.getInstance();
	        try {
	        	Connection connection2 = dbHandler.getConnection();
				 PreparedStatement preparedStatement2 = connection2.prepareStatement("SELECT * FROM utente WHERE codiceFiscale = ?");
				 preparedStatement2.setString(1,codiceFiscale );
				 ResultSet resultSet = preparedStatement2.executeQuery();
				
				 if(resultSet.next() == true) {
					
					 writer.append("\n<html><body><h1> L'utente "+nome+", risulta gia registrato"
					 		+ "<p><a href=\"pagregistrazione.html\">Torna alla registrazione</a></p>"
					 		+ "</h1></body></html> ");
				 }else {
					 try {
			        	 Connection connection = dbHandler.getConnection();
				            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO utente"
		                            + "(codiceFiscale,"
		                            + "nome,"
		                            + "cognome,"
		                            + "sesso,"
		                            + "luogoNascita,"
		                            + "dataNascita,"
		                            + "password,"
		                            + "provincia,"
		                            + "social) "
		                            + "VALUES (?,?,?,?,?,?,?,?,?)");
				            		
	
				           preparedStatement.setString(1,codiceFiscale );
				           
				           preparedStatement.setString(2, nome);
				           preparedStatement.setString(3, cognome);
				           preparedStatement.setString(4, String.valueOf(sesso));
				           preparedStatement.setString(5, luogoNascita);
				           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				           LocalDate localDate = LocalDate.parse(dataNascita, formatter);
				           preparedStatement.setDate(6, java.sql.Date.valueOf(localDate));
				           preparedStatement.setString(7, password);
				           preparedStatement.setString(8, provincia);
				           preparedStatement.setString(9, social);
				           
				           preparedStatement.executeUpdate();
				           
				       } catch (SQLException e) {
				    	   response.getWriter().append("Errore nel caricamento dei dati: "+e);
				    	   e.printStackTrace();
				       }
				       
					 	
						writer.append("\n<html><body><h1> Benvenuto "+nome+", la registrazione e' stata effettuata correttamente"
								+ "<p><a href=\"index.html\">Torna alla homepage</a></p>"
								+ "</h1></body></html> ");
				 }
			} catch (SQLException e1) {
				response.getWriter().append("Errore nel caricamento di dati"+ e1);
			}
	        
	        
		doGet(request, response);
	}

}
