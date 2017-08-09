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
@WebServlet(urlPatterns = { "/foodtype" })
public class VegData extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public VegData() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	 Connection conn=null;
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			String category=request.getParameter("foodtype");
			
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
				
				String urlquery1="select * from vegdata where foodtype=?";
				
				PreparedStatement ps1=conn.prepareStatement(urlquery1);
				
				ps1.setString(1, category);
				ResultSet rs1=ps1.executeQuery();
				JSONArray jarray = new JSONArray();
				
					if(rs1.next()){
						
					while(rs1.next()){
					String url=rs1.getString(2);
					String itemname1=rs1.getString(3);
				
						String urlquery11="select * from vegtable where foodtype=? and category = ?";
						PreparedStatement ps11=conn.prepareStatement(urlquery11);
						
						ps11.setString(1, itemname1);
						ps11.setString(2, category);
						ResultSet rs11=ps11.executeQuery();
						JSONArray jarray1 = new JSONArray();
						
						if(rs11.next()){
							
							while(rs11.next()){
							String url1=rs11.getString(1);
							String itemname11=rs11.getString(2);
							String cost = rs11.getString(3);
							String description = rs11.getString(4);
							String foodtype = rs11.getString(5);
							String availability = rs11.getString(7);
							String discount = rs11.getString(8);
						
								JSONObject jsonobj1 = new JSONObject();
								
								jsonobj1.put("URL", url1);
								jsonobj1.put("ItemName", itemname11);
								jsonobj1.put("Cost", cost);
								jsonobj1.put("Description", description);
								jsonobj1.put("foodtype", foodtype);
								jsonobj1.put("availability",availability);
								jsonobj1.put("discount", discount);
								jarray1.put(jsonobj1);
							}
							
					}
						JSONObject jsonobj = new JSONObject();
						jsonobj.put("ItemName", itemname1);	
						jsonobj.put("URL", url);
						jsonobj.put("subproducts", jarray1);
						jarray.put(jsonobj);
				
				
				}
					JSONObject jobj1= new JSONObject();
					String success="3";
			         
			         jobj1.put("response", success);
			         jobj1.put("products", jarray);
			        
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

