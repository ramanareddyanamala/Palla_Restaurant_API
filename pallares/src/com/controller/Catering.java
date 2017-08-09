package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Catering extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	   
    public Catering() {
        super();
      
    }

    Connection conn=null;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String logindetailes=request.getParameter("cater");
		
		String [] split=logindetailes.split(",");
		
		String name=split[0];
		String mobileno=split[1];
		
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/palla", "root", "vedas");
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		try {
			String insertpin="INSERT INTO CATERING VALUES(?,?)";
        	PreparedStatement ps=conn.prepareStatement(insertpin);
        	ps.setString(1, name);
        	ps.setString(2, mobileno);
        	int rowinsert = ps.executeUpdate();
			if(rowinsert>0){
				
				 JSONObject jsonobj = new JSONObject();
	        	 jsonobj.put("customerName", name);
	        	 jsonobj.put("customerPhoneNo", mobileno);
	        	 String message = "3";
	        	 jsonobj.put("response", message);
	        	 out.println(jsonobj);
	        	 
		   }
			else{
				String e= "0" ;
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("response", e);
				out.println(jsonobj);
				
				
			}
		
	}
		catch (Exception e) {  
			 e.printStackTrace();
		    throw new RuntimeException(e);  
		 }
	}
	

}
