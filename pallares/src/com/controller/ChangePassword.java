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


public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ChangePassword() {
        super();
        
    }

    Connection conn=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String detailes=request.getParameter("changepssdetailes");
		String [] split=detailes.split(",");
		String mailid=split[0];
		String password=split[1];
		String newpassword = split[2];
		
		
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/palla", "root", "vedas");
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		if(EmailValidate.checkUser(mailid, password)){
		 
			try{
			
			String emailquery="select mailid from login where mailid=?";
		
			PreparedStatement ps=conn.prepareStatement(emailquery);
			
			ps.setString(1,mailid);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				String updatequery="update login set password=? where mailid=?";
				PreparedStatement ps1=conn.prepareStatement(updatequery);
				ps1.setString(1, newpassword);
				ps1.setString(2, mailid);
				int updaterow=ps1.executeUpdate();
				if(updaterow>0){
					
					String message="3";
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("response", message);
					out.println(jsonobj);	
					
				}
				else{
					String message="0";
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("response", message);
					out.println(jsonobj);
					
				}
		
			}
			
			
		  }
		catch (SQLException | JSONException e) {
			
			e.printStackTrace();
		}
		
			finally{

            if (conn != null) {
                
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        
		
		}
		
		
	}
	
	else{
		
		String message = "0";
		JSONObject jsonobj = new JSONObject();
		try {
			jsonobj.put("response", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jsonobj);
	
	}
	}	
}
