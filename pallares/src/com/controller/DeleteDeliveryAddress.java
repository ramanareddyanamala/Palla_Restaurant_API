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
import org.json.JSONObject;

@WebServlet(urlPatterns = { "/deleteaddress" })
public class DeleteDeliveryAddress  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/palla";

	   static final String USER = "root";
	   static final String PASS = "vedas";
	   
	
	public void init(ServletConfig config) {

}
	
	
public DeleteDeliveryAddress() {
super();
// TODO Auto-generated constructor stub
}

Connection conn=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		String detailes=request.getParameter("daddress");
		
		
		String[] split = detailes.split(",");
		
		String mailid = split[0];
		String address_id =split[1];
		
		
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
			String insertpin="DELETE FROM  deliveryaddress  where address_id=? and mailid=?";
			PreparedStatement ps=conn.prepareStatement(insertpin);
			
			ps.setString(1, address_id);
			ps.setString(2, mailid);
	
			 int rowinsert = ps.executeUpdate();
		
			if(rowinsert>0){
				
				 JSONObject jsonobj = new JSONObject();
				 String msg = "3";
	        	 try {
					jsonobj.put("response", msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				out.println(jsonobj);
				
				
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
