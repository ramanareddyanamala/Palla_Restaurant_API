package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONException;
//import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(urlPatterns = { "/cart" })
public class Cart extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/palla";

	   static final String USER = "root";
	   static final String PASS = "vedas";
	   
	
	public void init(ServletConfig config) {
  
}
	
	
public Cart() {
  super();
  // TODO Auto-generated constructor stub
}

Connection conn=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		String detailes=request.getParameter("cart");
		
		String  mailid = null;
		String itemname = null;
		int price = 0;
		int quantity = 0;
		int rowinsert =0;
		double discount = 0 ;
		try{
				
			final JSONObject obj = new JSONObject(detailes);
			
			  mailid = obj.getString("mailid");
		      itemname = obj.getString("itemname");
			  price = obj.getInt("price");
			  quantity = obj.getInt("quantity");
			  discount = obj.getDouble("discount");
				
		}  catch(Exception e){
			
			
		} 
			      
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		try {
			String insertpin="INSERT INTO cart VALUES(?,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(insertpin);
			ps.setString(1,  mailid );
			ps.setString(2, itemname );
			ps.setInt(3, price);
			ps.setInt(4, quantity);
			ps.setDouble(5, discount);
  	
			 	rowinsert = ps.executeUpdate();
		
				}
		
				  catch (Exception e) {  
							 e.printStackTrace();
						    throw new RuntimeException(e);  
						 }
			    
			if(rowinsert>0){
				 JSONObject jsonobj = new JSONObject();
				 String msg = "3";
	        	 try {
					jsonobj.put("response", msg);
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
	        	 out.println(jsonobj);
	        
			    
				   
			}
			else{
				String e= "0" ;
				JSONObject jsonobj = new JSONObject();
				try {
					jsonobj.put("response", e);
				} catch (JSONException e1) {
					
					e1.printStackTrace();
				}
				out.println(jsonobj);
				
				
			}
			
			

      if (conn != null) {
          
          try {
              conn.close();
          } catch (SQLException ex) {
              ex.printStackTrace();
          }
      }
      
	}
}
