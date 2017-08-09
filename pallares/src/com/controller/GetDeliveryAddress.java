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
@WebServlet(urlPatterns = {"/getdaddress"})
public class GetDeliveryAddress extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public GetDeliveryAddress() {
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
				
				String urlquery1="select * from deliveryaddress where mailid=?";
				
				PreparedStatement ps1=conn.prepareStatement(urlquery1);
				
				ps1.setString(1, mailid);
				ResultSet rs1=ps1.executeQuery();
				
				JSONArray jarray = new JSONArray();
				
				while(rs1.next()){
					
					String address_id = rs1.getString(1);
					String isactive = rs1.getString(2);
					String name=rs1.getString(3);
					String phone=rs1.getString(4);
					String address =rs1.getString(5);
					String landmark = rs1.getString(6);
					String mailid2 = rs1.getString(7);
					
				
						JSONObject jsonobj = new JSONObject();
						jsonobj.put("name", name);	
						jsonobj.put("phone", phone);
						jsonobj.put("address", address);
						jsonobj.put("landmark", landmark);
						jsonobj.put("address_id", address_id);
						jsonobj.put("mailid", mailid2);
						jsonobj.put("isactive", isactive);
						jarray.put(jsonobj);
					  }
				   if(rs1.first()){
					
					 JSONObject jobj1= new JSONObject();
					 String success="3";
			         jobj1.put("response", success);
			         jobj1.put("addressdata", jarray);
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
