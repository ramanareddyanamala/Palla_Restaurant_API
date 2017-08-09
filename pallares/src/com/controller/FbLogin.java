package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
@WebServlet(urlPatterns = { "/fb" })
public class FbLogin extends HttpServlet {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/palla";

	   static final String USER = "root";
	   static final String PASS = "vedas";
	   
	
	public void init(ServletConfig config) {
     
 }
	
	private static final long serialVersionUID = 1L;

 public FbLogin() {
     super();
     // TODO Auto-generated constructor stub
 }

 Connection conn=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		String detailes=request.getParameter("fb");
		String [] split=detailes.split(",");
		
		
		String name = split[0];
		String mailid = split[1];
		
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
		String st ="select * from fb where mailid=?";
		PreparedStatement ps1=conn.prepareStatement(st);
		ps1.setString(1, mailid);
		ResultSet rs = ps1.executeQuery();
	    if(rs.first()){
	    	JSONObject jsonobj = new JSONObject();
	    	String username =rs.getString("name");
	    	String mailid1 =rs.getString("mailid");
	    	jsonobj.put("username", username);
	    	jsonobj.put("mailid", mailid1 );
       	 	String response1 = "3";
       	 	jsonobj.put("response" , response1);
       	 	out.println(jsonobj);
	    }else {
	    	try{
	    		String str = "select * from login where mailid =?";
	    		PreparedStatement ps2 = conn.prepareStatement(str);
	    		ps2.setString(1, mailid);
	    		ResultSet rs2 = ps2.executeQuery();
	    		if(rs2.next()){
	    			JSONObject jsonobj = new JSONObject();
	    			String response1 = "0";
	           	 	jsonobj.put("response" , response1);
	           	 	out.println(jsonobj);
	    		}else{
	    			try {
	    				String insertpin="INSERT INTO FB VALUES(?,?)";
	    		     	PreparedStatement ps=conn.prepareStatement(insertpin);
	    		     
	    		     	ps.setString(1, name);
	    		     	ps.setString(2, mailid );
	    		     	
	    					int rowinsert = ps.executeUpdate();
	    					if(rowinsert>0){
	    						
	    						 JSONObject jsonobj = new JSONObject();
	    			        	 jsonobj.put("username", name);
	    			        	 jsonobj.put("mailid", mailid );
	    			        	 String response1 = "3";
	    			        	 jsonobj.put("response" , response1);
	    			        	 out.println(jsonobj);
	    			        	
	    						}
	    					else{
	    						String e= "0" ;
	    						JSONObject jsonobj = new JSONObject();
	    						jsonobj.put("response", e);
	    						out.println(jsonobj);
	    						
	    					}
	    				
	    				              } catch (Exception e) {  
	    									 e.printStackTrace();
	    								    throw new RuntimeException(e);  
	    						 }				
	    				}
	    					
	    		}
	    		
	    	
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
		
			
	    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
		if (conn != null) {
             // closes the database connection
             try {
                 conn.close();
             } catch (SQLException ex) {
                 ex.printStackTrace();
             }
         }
     
		
		}
		
}
