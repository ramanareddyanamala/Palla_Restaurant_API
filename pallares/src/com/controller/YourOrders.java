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

@WebServlet(urlPatterns = {"/yourorders"})
public class YourOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public YourOrders() {
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
				
				String urlquery1="select * from orders where mailid=?";
				
				PreparedStatement ps1=conn.prepareStatement(urlquery1);
				
				ps1.setString(1, mailid);
				ResultSet rs1=ps1.executeQuery();
				
				JSONArray jarray = new JSONArray();
				
				while(rs1.next()){
					String itemname=rs1.getString(3);
					String price=rs1.getString(4);
					String quantity =rs1.getString(5);
					String totalcost=rs1.getString(11);
					
					String[] t2 = totalcost.split(",");
					String itemcost = t2[0];
					String totcost = t2[1];
				
						JSONObject jsonobj = new JSONObject();
						jsonobj.put("itemname", itemname);	
						jsonobj.put("price", price);
						jsonobj.put("quantity", quantity);
						jsonobj.put("totalcost", totcost);
						jarray.put(jsonobj);
					  }
				if(rs1.first()){
					 JSONObject jobj1= new JSONObject();
					 String success="3";
			         jobj1.put("response", success);
			         jobj1.put("yourordersdata", jarray);
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
