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

@WebServlet(urlPatterns = { "/updateaddress" })
public class UpdateDeliveryAddress extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/palla";

	   static final String USER = "root";
	   static final String PASS = "vedas";
	   
	
	public void init(ServletConfig config) {
	}
	
	
public UpdateDeliveryAddress() {
super();
// TODO Auto-generated constructor stub
}

Connection conn=null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		String detailes=request.getParameter("daddress");
		
		String name =null;
		String phone= null;
		String address= null;
		String landmark= null;
		String mailid = null;
		String address_id = null;
		String isactive = null;
		
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
			
		final JSONObject obj = new JSONObject(detailes);
		name = obj.getString("name");
	    phone= obj.getString("phone");
		address = obj.getString("address");
		landmark = obj.getString("landmark");
		mailid = obj.getString("mailid");
		address_id = obj.getString("address_id");
		isactive = obj.getString("isactive");
		
	
		}catch(JSONException e){
			e.printStackTrace();
		}
		int active = Integer.parseInt(isactive);
		
		if(active == 1){
		  try{ 
			int uactive=0;
			String update = "update deliveryaddress set isactive = ? where mailid = ?";
			PreparedStatement ps1=conn.prepareStatement(update);
			ps1.setInt(1,uactive);
			ps1.setString(2, mailid);
			int row = ps1.executeUpdate();
			if(row>0){
				 JSONObject jsonobj = new JSONObject();
				 String msg = "3";
				 jsonobj.put("response", msg);
				
			}
		 }catch(Exception e){
			 e.printStackTrace();
		 }  
		  try{
				
				String insertpin="UPDATE  deliveryaddress set name =?, phone=? , address=?, landmark=?, isactive=? where mailid=? and address_id=? ";
				PreparedStatement ps=conn.prepareStatement(insertpin);
				ps.setString(1, name );
				ps.setString(2, phone );
				ps.setString(3, address);
				ps.setString(4, landmark);
				ps.setInt(5, active);
				ps.setString(6, mailid);
				ps.setString(7, address_id);

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
		  
		  
		}else{
		  
			try{
		
		String insertpin="UPDATE  deliveryaddress set name =?, phone=? , address=?, landmark=? where mailid=? and address_id=? ";
		PreparedStatement ps=conn.prepareStatement(insertpin);
		ps.setString(1, name );
		ps.setString(2, phone );
		ps.setString(3, address);
		ps.setString(4, landmark);
		ps.setString(5, mailid);
		ps.setString(6, address_id);

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
				jsonobj.put("reponse", e);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			out.println(jsonobj);
			
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	}
}
