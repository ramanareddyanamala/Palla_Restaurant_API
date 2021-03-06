package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(urlPatterns = { "/getcart" })
public class GetCartdata extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public GetCartdata() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	 Connection conn=null;
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			String mailid=request.getParameter("mailid");
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/palla", "root", "vedas");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
				
				String urlquery1="select * from cart where mailid=?";
				
				PreparedStatement ps1=conn.prepareStatement(urlquery1);
				
				ps1.setString(1, mailid);
				ResultSet rs1=ps1.executeQuery();
				JSONArray jarray = new JSONArray();
				
					while(rs1.next()){
					String itemname=rs1.getString(2);
					int price=rs1.getInt(3);
					int quantity =rs1.getInt(4);
					double discount = rs1.getDouble(5);
				
						JSONObject jsonobj = new JSONObject();
						jsonobj.put("ItemName", itemname);	
						jsonobj.put("price", price);
						jsonobj.put("quantity", quantity);
						jsonobj.put("discount", discount);
						jarray.put(jsonobj);
					  }
					if(rs1.first()){
					 JSONObject jobj1= new JSONObject();
					 String success="3";
			         jobj1.put("response", success);
			         jobj1.put("cartdata", jarray);
			         out.println(jobj1); 
			         
			         
					}else{
		 			String message="0";
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("response", message);
					out.println(jsonobj);	
		 		}
				 
				}catch (SQLException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch(JSONException e){
					e.printStackTrace();
				}
				finally{

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
		
}
