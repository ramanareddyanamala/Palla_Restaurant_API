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
@WebServlet(urlPatterns = { "/insertaddress" })
public class InsertDeliveryAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/palla";

	   static final String USER = "root";
	   static final String PASS = "vedas";
	   
	
	public void init(ServletConfig config) {
  
}
	
	
public InsertDeliveryAddress() {
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
	
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{ 
			  int isactive = 0;
			String update = "update deliveryaddress set isactive = ? where mailid = ?";
			PreparedStatement ps1=conn.prepareStatement(update);
			ps1.setInt(1,isactive);
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
		
		try {
			int isactive = 1;
			String insertpin="INSERT INTO deliveryaddress VALUES(?,?,?,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(insertpin);
			ps.setString(1, address_id);
			ps.setInt(2, isactive);
			ps.setString(3, name );
			ps.setString(4, phone );
			ps.setString(5, address);
			ps.setString(6, landmark);
			ps.setString(7, mailid);
			
	
			 int rowinsert = ps.executeUpdate();
		
			  if(rowinsert>0){
				  try{
				  JSONObject jsonobj = new JSONObject();
					 String msg = "3";
					 jsonobj.put("response", msg);
					 out.println(jsonobj);
				   
				  }catch(JSONException e){
					  e.printStackTrace();
				  }
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
		}
		catch (SQLException ex) {
            ex.printStackTrace();
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
