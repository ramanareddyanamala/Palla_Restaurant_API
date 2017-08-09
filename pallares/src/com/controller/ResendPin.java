package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(urlPatterns = {"/resend"})
public class ResendPin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ResendPin() {
        super();
        // TODO Auto-generated constructor stub
    }

	Connection conn=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String detailes=request.getParameter("resenddetailes");
		
		String [] split=detailes.split(",");
		
		String username=split[0];
		String mailid=split[1];
		String mobileno=split[2];
		
		
		
		@SuppressWarnings("unused")
		String apipassword="7799338800";
		@SuppressWarnings("unused")
		String to= mobileno;
		@SuppressWarnings("unused")
		String from="TRATRE";
		String pin = String.valueOf((int)(((Math.random())*1000)+2990));
		String host="mail.8locations.com.tw";
		
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
				Statement statement;
		
				statement = conn.createStatement();
		
				ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM pindetailes");
				rs.next();
				int rowCount=rs.getInt(1);
				if(rowCount==0){
					
					
					
				            	 Properties properties = new Properties();
			 						properties.put("mail.smtp.host", host);
			 						properties.put("mail.smtp.auth", "true");
			 						Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
			 							protected PasswordAuthentication getPasswordAuthentication() {
			 								return new PasswordAuthentication("Service@8locations.com.tw","clw1030730");
			 							}
			 						}
			 								);
			 						
			 						try {  
			 							 MimeMessage message = new MimeMessage(session);  
			 							 message.setFrom(new InternetAddress("Service@8locations.com.tw"));  
			 							 message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailid));  
			 							// message.addRecipient(Message.RecipientType.TO,new InternetAddress("venkimurthy07@gmail.com")); 
			 							 message.setSubject("Palla Restaurant Registration Confirmation - Resend");  
			 							/* message.setText("Dear "+username+","+'\n'
				 									+'\n'+"We have received a request for this email address to be registered to Bag++.In order to add you to our registered member database, we need you to confirm your request."+'\n'+'\n'+
				 									 "Please find the below verification number to confirm your registration."	+'\n'+'\n'	
				 									+"Your Bag++ Account verification number is:"+pin+'\n' +'\n'+
				 									"Also, please find below your login details for your records. We request you to keep these safe and confidential."+'\n'+'\n'+
				 									"Your Login Id is: "+ username+'\n'+'\n'+
				 									"This is a computer-generated email and reply to this is not monitored."+'\n'+'\n'+
				 									"Best Wishes,"+'\n'+
				 									"Bag++ Team."+'\n'+'\n'+
				 									"Please note: If you have not attempted to register with Bag++, please ignore this email.");*/  
			 							 
			 							String msg="Dear "+username+","+"<br><br>"
			 									+"We have received a request for this email address to be registered to Palla Restaurant.In order to add you to our registered member database, we need you to confirm your request."+"<br><br>"+
			 									 "Please find the below verification number to confirm your registration."	+"<br><br>"	
			 									
			 									+"<font color=blue>"+"Your Palla Restaurant Account verification number is: "+pin+"</font>"+"<br><br>"+
			 									
			 									"Also, please find below your login details for your records. We request you to keep these safe and confidential."+"<br><br>"
			 									
													+"<font color=blue>"+"Your Login Id is: "+username+"</font>" +"<br><br>"+
			 									
			 									"This is a computer-generated email and reply to this is not monitored."+"<br><br>"+
			 									"Best Wishes,"+"<br>"+
			 									"Palla Team."+"<br><br>"+
			 									
			 									"Please note: If you have not attempted to register with Palla, please ignore this email.";
			 							
			 							 message.setContent(msg,"text/html");
			 							
			 							 Transport.send(message);  
			 							  
			 							
			 							String insertpin="INSERT INTO pindetailes (mobileno  , pinnumber ) values (?, ? )";
						            	PreparedStatement ps1=conn.prepareStatement(insertpin);
						            	ps1.setString(1, mobileno);
						            	ps1.setString(2, pin);
										int rowinsert=ps1.executeUpdate();
										
										if(rowinsert>0){
											String success="3";
											JSONObject jsonobj = new JSONObject();
											jsonobj.put("response", success);
											out.println(jsonobj);
										}
										String s = "update pindetailes set pinnumber=? where mobileno=?"; 
										PreparedStatement psmt = conn.prepareStatement(s);
										psmt.setString(1, pin);
										psmt.setString(2, mobileno);
			 							  
			 							 } catch (MessagingException | JSONException e) {  
			 								 e.printStackTrace();
			 							    throw new RuntimeException(e);  
			 							 }
								
				            	
				            }else{
				            	
				            	 	Properties properties = new Properties();
			 						properties.put("mail.smtp.host", host);
			 						properties.put("mail.smtp.auth", "true");
			 						Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
			 							protected PasswordAuthentication getPasswordAuthentication() {
			 								return new PasswordAuthentication("Service@8locations.com.tw","clw1030730");
			 							}
			 						}
			 								);
			 						
			 						try {  
			 							 MimeMessage message = new MimeMessage(session);  
			 							 message.setFrom(new InternetAddress("Service@8locations.com.tw"));  
			 							 message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailid));  
			 							// message.addRecipient(Message.RecipientType.TO,new InternetAddress("venkimurthy07@gmail.com")); 
			 							 message.setSubject("Palla Restaurant Registration Confirmation - Resend");  
			 							/* message.setText("Dear "+username+","+'\n'
				 									+'\n'+"We have received a request for this email address to be registered to Bag++.In order to add you to our registered member database, we need you to confirm your request."+'\n'+'\n'+
				 									 "Please find the below verification number to confirm your registration."	+'\n'+'\n'	
				 									+"Your Bag++ Account verification number is:"+pin+'\n' +'\n'+
				 									"Also, please find below your login details for your records. We request you to keep these safe and confidential."+'\n'+'\n'+
				 									"Your Login Id is: "+ username+'\n'+'\n'+
				 									"This is a computer-generated email and reply to this is not monitored."+'\n'+'\n'+
				 									"Best Wishes,"+'\n'+
				 									"Bag++ Team."+'\n'+'\n'+
				 									"Please note: If you have not attempted to register with Bag++, please ignore this email.");*/  
			 							String msg="Dear "+username+","+"<br><br>"
			 									+"We have received a request for this email address to be registered to Palla Restaurant.In order to add you to our registered member database, we need you to confirm your request."+"<br><br>"+
			 									 "Please find the below verification number to confirm your registration."	+"<br><br>"	
			 									
			 									+"<font color=blue>"+"Your Palla Restaurant Account verification number is: "+pin+"</font>"+"<br><br>"+
			 									
			 									"Also, please find below your login details for your records. We request you to keep these safe and confidential."+"<br><br>"
			 									
													+"<font color=blue>"+"Your Login Id is: "+username+"</font>" +"<br><br>"+
			 									
			 									"This is a computer-generated email and reply to this is not monitored."+"<br><br>"+
			 									"Best Wishes,"+"<br>"+
			 									"Palla Team."+"<br><br>"+
			 									
			 									"Please note: If you have not attempted to register with Palla Restaurant, please ignore this email.";
			 							
			 							 message.setContent(msg,"text/html");
			 							   
			 							 //3rd step)send message  
			 							 Transport.send(message); 
			 							String phonequery="select mobileno from pindetailes where mobileno=?";
			 							PreparedStatement ps2=conn.prepareStatement(phonequery);
			 							ps2.setString(1, mobileno);
			 							ResultSet rs2=ps2.executeQuery();
			 							if(rs2.next()){
			 								String updatepinnum="update pindetailes set pinnumber=? where mobileno=?";
						            		PreparedStatement ps3=conn.prepareStatement(updatepinnum);
						    				ps3.setString(1, pin);
						    				ps3.setString(2, mobileno);
						    				int updaterbagpin=ps3.executeUpdate();
						    				if(updaterbagpin>0){
						    					String success="3";
												JSONObject jsonobj = new JSONObject();
												jsonobj.put("response", success);
												out.println(jsonobj);
						    				}
						            	
						            
						            }else{
						            	String insertpin="INSERT INTO pindetailes (mobileno  , pinnumber ) values (?, ? )";
						            	PreparedStatement ps4=conn.prepareStatement(insertpin);
						            	ps4.setString(1, mobileno);
						            	ps4.setString(2, pin);
										int rowinsert=ps4.executeUpdate();
										if(rowinsert>0){
											String success="3";
											JSONObject jsonobj = new JSONObject();
											jsonobj.put("response", success);
											out.println(jsonobj);
										}
						            	
						            	
						            	
						            }
				            	
				            	
				            	
			 						 } catch (MessagingException | JSONException e) {  
		 								 e.printStackTrace();
		 							    throw new RuntimeException(e);  
		 							 }
				            	
				            	
				            	
				            	
				            
				            
								
				       
				            }
					
					
					
				
		
		} catch (SQLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{


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
