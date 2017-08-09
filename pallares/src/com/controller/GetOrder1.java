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
import org.json.JSONObject;

@WebServlet(urlPatterns = {"/getorders1"})
public class GetOrder1 extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public GetOrder1() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	 Connection conn=null;
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			String mailid=request.getParameter("mailid");
			String orderid = null;
			String timestamp = null;
			String address = null;
			String landmark = null;
			String paymenttype = null;
			String  totaldiscount = null;
			String  netamount = null;
			String name = null;
			String phone=null; 
			String totalcost=null;
			int deliverystatus = 0;
			JSONArray  jarray123 = null, jarray1234=null;
			
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
				String urlquery1="select DISTINCT orderid from orders where mailid=?";
				PreparedStatement ps1=conn.prepareStatement(urlquery1);
				ps1.setString(1, mailid);
				ResultSet rs1=ps1.executeQuery();
				
				JSONArray jarray = new JSONArray();
				
				while(rs1.next()){
					
				orderid=rs1.getString(1);
				
				try{
			       String query123="select * from orders where orderid=?";
					PreparedStatement ps123=conn.prepareStatement(query123);
					ps123.setString(1, orderid);
					
					ResultSet rs123=ps123.executeQuery();
					jarray123 = new JSONArray();
					
					while(rs123.next()){
						
						String itemname=rs123.getString(3);
						String price=rs123.getString(4);
						String quantity =rs123.getString(5);
						Double discount=rs123.getDouble(6);
						
						int price1 = Integer.parseInt(price);
						int qty = Integer.parseInt(quantity);
						
						JSONObject jsonobj=new JSONObject();
						jsonobj.put("itemname", itemname);	
						jsonobj.put("price", price1);
						jsonobj.put("quantity", qty);
						jsonobj.put("discount", discount);
						jarray123.put(jsonobj);
						}
					
					try{
						String query="select * from orders where orderid=?";
						PreparedStatement ps12=conn.prepareStatement(query);
						ps12.setString(1, orderid);
						ResultSet rs12=ps12.executeQuery();
						
						jarray1234 = new JSONArray();
					    if(rs12.next()){
							 name = rs12.getString(7);
							 phone = rs12.getString(8);
							 address=rs12.getString(9);
							 landmark=rs12.getString(10);
							 JSONObject jobj1 = new JSONObject();
							 jobj1.put("address", address);
					         jobj1.put("name", name);
					         jobj1.put("phone", phone);
					         jobj1.put("landmark", landmark);
					         jarray1234.put(jobj1);
					    }
					    }
			         catch(Exception e){
			        	 e.printStackTrace();
				  }
					try{
						String query0="select * from orders where orderid=?";
						PreparedStatement ps120=conn.prepareStatement(query0);
						ps120.setString(1, orderid);
						ResultSet rs120=ps120.executeQuery();
						
					    if(rs120.next()){
					    	 paymenttype = rs120.getString(11);
					    	 totaldiscount = rs120.getString(12);
					 		 netamount = rs120.getString(13);
					    	 totalcost=rs120.getString(14);
					    	 timestamp = rs120.getString(15);
					    	 deliverystatus=rs120.getInt(16);
					    	 
					    }	
					}
					catch(Exception e){
						e.printStackTrace();
					}
					
			   
				JSONObject jb1=new JSONObject();
				jb1.put("orderid", orderid);
				jb1.put("paymenttype", paymenttype);
				jb1.put("totaldiscount", totaldiscount);
				jb1.put("netamount", netamount);
				jb1.put("totalcost", totalcost);
				jb1.put("timestamp", timestamp);
				jb1.put("delivery status", deliverystatus);
				jb1.put("items", jarray123);
				jb1.put("address", jarray1234);
				
				jarray.put(jb1);
			 }
			    
				catch(Exception e){
					e.printStackTrace();
				}
			
				} 
			 if(rs1.first()){
				JSONObject jb=new JSONObject();
				String s = "3";
				jb.put("response", s);
				jb.put("orders", jarray);
				out.println(jb);
				
			 }
			 else{
				 JSONObject js = new JSONObject();
				 String s = "0";
				 js.put("response",s);
				 out.println(js);
				
			 }
				}catch(Exception e){
					e.printStackTrace();
				}
		}
}
