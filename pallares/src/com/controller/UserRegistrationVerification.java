package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class BagRegistration
 */

public class UserRegistrationVerification extends HttpServlet {
private static final long serialVersionUID = 1L;
       
    
    public UserRegistrationVerification() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) {
        
    }

	Connection conn=null;
	PreparedStatement ps;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		
		String detailes=request.getParameter("verifydetailes");
		
		String [] split=detailes.split(",");
		
		String username=split[0];
		String password=split[1];
		String mailid=split[2];
		String mobileno=split[3];
		String address=split[4];
		String pin=split[5];
		
		 String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		  int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			StringBuilder builder = new StringBuilder();
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
			int randomPIN = (int)(Math.random()*900)+100;
			builder.toString();
			builder.append(randomPIN);

		
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
				
				String querypin="select pinnumber  from pindetailes where mobileno=?";
				PreparedStatement ps1=conn.prepareStatement(querypin);
				ps1.setString(1, mobileno);
				ResultSet rs1=ps1.executeQuery();
				
				if(rs1.next()){
			 		String pinnumber=rs1.getString(1);
			 		
			 		if(pinnumber.equals(pin)){
			 			
			 			String query="INSERT INTO login (username  ,password  ,mailid, mobileno, address ) values (?, ?,?,?,? )";
			 			PreparedStatement ps2=conn.prepareStatement(query);
						
			 			ps2.setString(1, username);
						ps2.setString(2, password);
						ps2.setString(3, mailid);
						ps2.setString(4, mobileno);
						ps2.setString(5, address);
						
						int row=ps2.executeUpdate();
						
						
						String message1="3";
						JSONObject jsonobj1 = new JSONObject();
						jsonobj1.put("response", message1);
						out.println(jsonobj1);
						
						
					if(row>0){
							
									String deletequery="delete from pindetailes where mobileno=?";
									PreparedStatement ps3=conn.prepareStatement(deletequery);
									ps3.setString(1, mobileno);
									int rs3=ps3.executeUpdate();
									System.out.println(rs3);
									
									
									String deletequery1="delete from registration where mobileno=?";
									PreparedStatement ps13=conn.prepareStatement(deletequery1);
									ps13.setString(1, mobileno);
									int rs13=ps13.executeUpdate();
									System.out.println(rs13);
									
									String message="3";
									JSONObject jsonobj = new JSONObject();
									jsonobj.put("response", message);
									out.println(jsonobj);
									
								}
			 		
			 		}
			
				else{
					String message="0";
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("response", message);
					out.println(jsonobj);
				}
					
			
	} 
			}catch (SQLException | JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
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


