package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public UserLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

    Connection conn=null;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String logindetailes=request.getParameter("logindetailes");
		
		String [] split=logindetailes.split(",");
		
		String mailid=split[0];
		String password=split[1];
		
	
		
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
		
		
		
		 if(EmailValidate.checkUser(mailid, password)){
			 try {
				
				String query="select username,mailid,password,mobileno,address from login where mailid=?";
				PreparedStatement ps=conn.prepareStatement(query);
				ps.setString(1, mailid);
				ResultSet rs = ps.executeQuery();
				while(rs.next())
				{
					
					String username =rs.getString("username");
					String password1 = rs.getString("password");
					String mailid1=rs.getString("mailid");
					String mobileno=rs.getString("mobileno");
					String address=rs.getString("address");
					
					String message1="3";
		
					JSONObject jsonobj2 = new JSONObject();
					jsonobj2.put("userrname", username);
					jsonobj2.put("password", password1);
					jsonobj2.put("mailid", mailid1);
					jsonobj2.put("mobileno", mobileno);
					jsonobj2.put("address", address);
					jsonobj2.put("response", message1);

					out.println(jsonobj2);
					
				
				}
				
				
			 } catch (JSONException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		 }
		
	
		 
		 else if(EmailValidate.checkRegister(mailid)){
			
			try{
			 String query="select username,mailid,password,mobileno,address from registration where mailid=?";
				PreparedStatement ps=conn.prepareStatement(query);
				ps.setString(1, mailid);
				ResultSet rs = ps.executeQuery();
				while(rs.next())
				{
					
					String username =rs.getString("username");
					String password1 = rs.getString("password");
					String mailid1=rs.getString("mailid");
					String mobileno=rs.getString("mobileno");
					String address=rs.getString("address");
					
					String message1="5";
		
					JSONObject jsonobj3 = new JSONObject();
					jsonobj3.put("userrname", username);
					jsonobj3.put("password", password1);
					jsonobj3.put("mailid", mailid1);
					jsonobj3.put("mobileno", mobileno);
					jsonobj3.put("address", address);
					jsonobj3.put("response", message1);

					out.println(jsonobj3);
					
				
				}
	
			
		 }
			catch(JSONException | SQLException e){
				e.printStackTrace();
				
			}
		 }
		 
		 else{
			 try {
				 String message="0";
				 JSONObject jsonobj = new JSONObject();
				
				jsonobj.put("response", message);
				out.println(jsonobj);
				
				
			 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
	}

}
