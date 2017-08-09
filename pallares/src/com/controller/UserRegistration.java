package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class UserRegistration extends HttpServlet   {
	private static final long serialVersionUID = 1L;
   
    public UserRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) {
       
    }
   

    Connection conn=null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		
		String detailes=request.getParameter("regdetailes");
		
		
		String [] split=detailes.split(",");
		
		String username=split[0];
		String password=split[1];
		String mailid=split[2];
		String mobileno=split[3];
		String address=split[4];
		
		String pin = String.valueOf((int)(((Math.random())*1000)+1990));
		
		String user = "username=" + "ramanareddy0012@gmail.com";
		String hash = "&hash=" + "27276afd8443fc1206d05cb8b04f5f244d7146e9b48765c8a5b8c13911556247";
		String message1 = "&message=" + "your verification code is "+pin;
		String sender = "&sender=" + "TXTLCL";
		String numbers = "&numbers=" + mobileno;
		
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
			String query2="select mobileno,mailid from login where mobileno=? and mailid=?";
			PreparedStatement ps1=conn.prepareStatement(query2);
		 	ps1.setString(1,mobileno);
		 	ps1.setString(2, mailid);
		 	ResultSet rs1=ps1.executeQuery();
		 	
		 	if(rs1.next()){
		 		
				String failure="2";
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("response", failure);
				out.println(jsonobj);
				
				 
		 	}
		    if(EmailValidate.checkFB(mailid)){
				 try {
						
						String query="select mailid from fb where mailid=?";
						PreparedStatement ps=conn.prepareStatement(query);
						ps.setString(1, mailid);
						ResultSet rs = ps.executeQuery();
						if(rs.first())
						{
						     String message12="6";
						     JSONObject jsonobj2 = new JSONObject();
						     jsonobj2.put("response", message12);
						     //out.println(jsonobj2);
						     System.out.println("FB restriction..."+jsonobj2);
						}
					  	
					  } catch (JSONException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				 }
		    
			Statement statement;
			statement = conn.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT COUNT(*) FROM login");
			resultset.next();
			int rowCount=resultset.getInt(1);
			
			if(rowCount==0){
			
				final String username1 = "pallarestaurant@gmail.com";
				final String password1 = "vasu1681";

				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username1, password1);
					}
				  });

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("pallarestaurant@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(mailid));
					 
					 String msg="<body style='background-color:'#B1998B'>"
								 + "<div class=wrap>"+
					               "<div class=logo>"+
				                    "<a href=#>"+
				               "<img src=http://52.70.91.75/Image/image?id=21 width = 100%>"+"</a>"
				               +"</div>"
				               
				          +"</div>"
				          +"</div>"+
						 			"<table background=  http://52.70.91.75/Image/image?id=17  width=100%>"+"</tr>"+"<tr>"+"<td align= left valign = middle   font-family:Arial, Helvetica, sans-serif;>"
								    +"<br><br>"+"<font size=4 color=#C70039>"+"<b>"+"Dear "+username+","+"<br><br>"
									+ "<div >"+"<font size=4 color=#000000>" +"<i>"+ "We have received a request for this email address to be registered to Palla Restaurant.In order to add you to our registered member database, we need you to confirm your request."+"<br><br>"+
									 "Please find the below verification number to confirm your registration."	+"<br><br>"	
									
									+"<font color=red>"+"<mark>"+"Your Palla Restaurant Account verification number is: "+"<h1>"+"<mark>"+pin+"</h1>"+"</font>"+"<br><br>"+
									
									"Also, please find below your login details for your records. We request you to keep these safe and confidential."+"<br><br>"
									
										+"<font color=#ff6600>"+"Your Login Id is: "+mailid+"</font>" +"<br><br>"+
									
									"This is a computer-generated email and reply to this is not monitored."+"<br><br>"+
									"Best Wishes,"+"<br>"+
									"Palla Team."+"<br><br>"+
									
									"Please note: If you have not attempted to register with Palla Restaurant, please ignore this email."
									+"</i>"+"</b>"+"</td>"+"</tr>"+"<tr bgcolor= white>"
									+"<td bgcolor='#564319' width=100%>"+"<tr>"+"<td>"+"<i>"+"<b>"+"<font size=4 color=#C70039>"+"Copy @ Right Reserved."+"<br>"+"please visit: www.pallarestaurant.com"+"</font>"+
									"</b>"+"</i>"+"</h2>"+"</td>"+"</tr>"+"</table>"+"</body>"
									;
					message.setSubject("Registration confirmation!!");
					message.setContent(msg,"text/html");

					Transport.send(message);


						 String insertreg="INSERT INTO registration (username  ,password  ,mailid, mobileno, address ) values (?,?,?,?,? )";
			            	PreparedStatement ps31=conn.prepareStatement(insertreg);
			            	ps31.setString(1, username);
			            	ps31.setString(2, password);
			            	ps31.setString(3, mailid);
			            	ps31.setString(4, mobileno);
			            	ps31.setString(5, address);
			            	
			            	
							int insert1=ps31.executeUpdate();
							if(insert1>0){
								String success="3";
								JSONObject jsonobj = new JSONObject();
								jsonobj.put("response", success);
								out.println(jsonobj);
							
						}
							try {
								HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
								String data = user + hash + numbers + message1 + sender;
								conn.setDoOutput(true);
								conn.setRequestMethod("POST");
								conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
								conn.getOutputStream().write(data.getBytes("UTF-8"));
								final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
								final StringBuffer stringBuffer = new StringBuffer();
								String line;
								
								while ((line = rd.readLine()) != null) {
									stringBuffer.append(line);
									
									out.println(line);
								}
								rd.close();
								
								}catch(Exception e){
									
									
								}
						
						    String phonequery="select mobileno from pindetailes where mobileno=?";
							PreparedStatement ps12=conn.prepareStatement(phonequery);
							ps12.setString(1, mobileno);
							ResultSet rs12=ps12.executeQuery();
							if(rs12.next()){
								String update="update pindetailes set pinnumber=? where mobileno=?";
			            		PreparedStatement ps2=conn.prepareStatement(update);
			    				ps2.setString(1, pin);
			    				ps2.setString(2, mobileno);
			    				int updatecount=ps2.executeUpdate();
			    				if(updatecount>0){
			    					String success="3";
									JSONObject jsonobj = new JSONObject();
									jsonobj.put("response", success);
									out.println(jsonobj);
			    				}
			    				}else{
								String insertpin="INSERT INTO pindetailes (mobileno  , pinnumber ) values (?, ? )";
				            	PreparedStatement ps3=conn.prepareStatement(insertpin);
				            	ps3.setString(1, mobileno);
				            	ps3.setString(2, pin);
								int insert=ps3.executeUpdate();
								if(insert>0){
									String success="3";
									JSONObject jsonobj = new JSONObject();
									jsonobj.put("response", success);
									out.println(jsonobj);
								}
								
							}
						 } catch (MessagingException e) {  
							 e.printStackTrace();
						    throw new RuntimeException(e);  
						 }
			
				
			}
			
		
			else{
				
				String query21="select mailid from login where mailid=?";
				PreparedStatement ps4=conn.prepareStatement(query21);
				ps4.setString(1,mailid);
				ResultSet rs4=ps4.executeQuery();
		 		if(rs4.next()){
		 			String message="2";
		 			JSONObject jsonobj = new JSONObject();
		 			jsonobj.put("response", message);
		 			out.println(jsonobj);
		 		}else{
		 			
		 				String query3="select mobileno from login where mobileno=?";
		 				PreparedStatement ps5=conn.prepareStatement(query3);
		 				ps5.setString(1,mobileno);
		 				ResultSet rs5=ps5.executeQuery();
		 				if(rs5.next()){
		 					String message="1";
		 					JSONObject jsonobj = new JSONObject();
		 					jsonobj.put("response", message);
		 					out.println(jsonobj);
		 				}
		 				else if(EmailValidate.checkFB(mailid)){
	    					 try {
	    							
	    							String query="select mailid from fb where mailid=?";
	    							PreparedStatement ps=conn.prepareStatement(query);
	    							ps.setString(1, mailid);
	    							ResultSet rs = ps.executeQuery();
	    							if(rs.first())
	    							{
	    							     String message12="6";
	    							     JSONObject jsonobj2 = new JSONObject();
	    							     jsonobj2.put("response", message12);
	    							     out.println(jsonobj2);
	    							     System.out.println("FB restriction..."+jsonobj2);
	    							}
	    						  	
	    						  } catch (JSONException | SQLException e) {
	    								// TODO Auto-generated catch block
	    								e.printStackTrace();
	    							}
	    							
	    					 }

		 				else{
			 		
		 					String emailquery="select mailid from login where mailid=?";
		 					PreparedStatement ps6=conn.prepareStatement(emailquery);
		 					ps6.setString(1,mailid);
		 					ResultSet rs6=ps6.executeQuery();
		 					if(rs6.next()){
		 						String message="2";
		 						JSONObject jsonobj = new JSONObject();
		 						jsonobj.put("response", message);
		 						out.println(jsonobj);
					 		
		 					}
		 					
		 					else{
		 						
		 						final String username1 = "pallarestaurant@gmail.com";
		 						final String password1 = "vasu1681";

		 						Properties props = new Properties();
		 						props.put("mail.smtp.auth", "true");
		 						props.put("mail.smtp.starttls.enable", "true");
		 						props.put("mail.smtp.host", "smtp.gmail.com");
		 						props.put("mail.smtp.port", "587");

		 						Session session = Session.getInstance(props,
		 						  new javax.mail.Authenticator() {
		 							protected PasswordAuthentication getPasswordAuthentication() {
		 								return new PasswordAuthentication(username1, password1);
		 							}
		 						  });

		 						try {

		 							Message message = new MimeMessage(session);
		 							message.setFrom(new InternetAddress("pallarestaurant@gmail.com"));
		 							message.setRecipients(Message.RecipientType.TO,
		 								InternetAddress.parse(mailid));
		 							message.setSubject("Palla Registration!!!!");
		 							String msg="<body style='background-color:'#B1998B'>"
		 									 + "<div class=wrap>"+
		 						               "<div class=logo>"+
		 					                    "<a href=#>"+
		 					               "<img src=http://52.70.91.75/Image/image?id=21 width = 100%>"+"</a>"
		 					               +"</div>"
		 					               
		 					               +"</div>"
		 					               +"</div>"+
		 							 			"<table background= http://52.70.91.75/Image/image?id=17  width=100%>"+"</tr>"+"<tr>"+"<td align= left valign = middle   font-family:Arial, Helvetica, sans-serif;>"
		 									    +"<br><br>"+"<font size=4 color=#C70039>"+"<b>"+"Dear "+username+","+"<br><br>"
		 										+ "<div >"+"<font size=4 color=#000000>" +"<i>"+ "We have received a request for this email address to be registered to Palla Restaurant.In order to add you to our registered member database, we need you to confirm your request."+"<br><br>"+
		 										 "Please find the below verification number to confirm your registration."	+"<br><br>"	
		 										
		 										+"<font color=red>"+"<mark>"+"Your Palla Restaurant Account verification number is: "+"<h1>"+"<mark>"+pin+"</h1>"+"</font>"+"<br><br>"+
		 										
		 										"Also, please find below your login details for your records. We request you to keep these safe and confidential."+"<br><br>"
		 										
		 											+"<font color=#ff6600>"+"Your Login Id is: "+mailid+"</font>" +"<br><br>"+
		 										
		 										"This is a computer-generated email and reply to this is not monitored."+"<br><br>"+
		 										"Best Wishes,"+"<br>"+
		 										"Palla Team."+"<br><br>"+
		 										
		 										"Please note: If you have not attempted to register with Palla Restaurant, please ignore this email."
		 										+"</i>"+"</b>"+"</td>"+"</tr>"+"<tr bgcolor= white>"
		 										+"<td bgcolor='#564319' width=100%>"+"<tr>"+"<td>"+"<i>"+"<b>"+"<font size=4 color=#C70039>"+"Copy @ Right Reserved."+"<br>"+"please visit: www.pallarestaurant.com"+"</font>"+
		 										"</b>"+"</i>"+"</h2>"+"</td>"+"</tr>"+"</table>"+"</body>"
		 										;
		 							message.setContent(msg,"text/html");

		 							Transport.send(message);


		 							String insertreg1="INSERT INTO registration (username  ,password  ,mailid, mobileno, address ) values (?, ?,?,?,? )";
		 			            	PreparedStatement ps32=conn.prepareStatement(insertreg1);
		 			            	ps32.setString(1, username);
		 			            	ps32.setString(2, password);
		 			            	ps32.setString(3, mailid);
		 			            	ps32.setString(4, mobileno);
		 			            	ps32.setString(5, address);
		 			            	
		 			            	
		 							int insert1=ps32.executeUpdate();
		 							if(insert1>0){
		 								String success="3";
		 								JSONObject jsonobj = new JSONObject();
		 								jsonobj.put("response", success);
		 								out.println(jsonobj);
		 						}
		 							try {
		 								HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
		 								String data = user + hash + numbers + message1 + sender;
		 								conn.setDoOutput(true);
		 								conn.setRequestMethod("POST");
		 								conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
		 								conn.getOutputStream().write(data.getBytes("UTF-8"));
		 								final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		 								final StringBuffer stringBuffer = new StringBuffer();
		 								String line;
		 								
		 								while ((line = rd.readLine()) != null) {
		 									stringBuffer.append(line);
		 									
		 									out.println(line);
		 								}
		 								rd.close();
		 								
		 								}catch(Exception e){
		 									
		 									
		 								}
		 						
		 							String phonequery="select mobileno from pindetailes where mobileno=?";
		 							PreparedStatement ps7=conn.prepareStatement(phonequery);
		 							ps7.setString(1, mobileno);
		 							ResultSet rs7=ps7.executeQuery();
		 							if(rs7.next()){
		 								String updatepinnum="update pindetailes set pinnumber=? where mobileno=?";
					            		PreparedStatement ps8=conn.prepareStatement(updatepinnum);
					    				ps8.setString(1, pin);
					    				ps8.setString(2, mobileno);
					    				int updaterbagpin=ps8.executeUpdate();
					    				if(updaterbagpin>0){
					    					String success="3";
											JSONObject jsonobj = new JSONObject();
											jsonobj.put("response", success);
											out.println(jsonobj);
					    				}
		 							}else{
		 								String insertpin="INSERT INTO pindetailes (mobileno  , pinnumber     ) values (?, ? )";
						            	PreparedStatement ps9=conn.prepareStatement(insertpin);
						            	ps9.setString(1, mobileno);
						            	ps9.setString(2, pin);
										int rowinsert=ps9.executeUpdate();
										if(rowinsert>0){
											String success="3";
											JSONObject jsonobj = new JSONObject();
											jsonobj.put("response", success);
											out.println(jsonobj);
										}
		 								
		 							}
		 							
		 							 
		 							 } catch (MessagingException e) {  
		 								 e.printStackTrace();
		 							    throw new RuntimeException(e);  
		 							 }
		 							
		 					}
			
		 				}
		 		}
			
			
		
			}
				
			
		} catch (SQLException | JSONException e) {
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
