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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
@WebServlet(urlPatterns = { "/orders" })
public class Order extends HttpServlet {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/palla";

	   static final String USER = "root";
	   static final String PASS = "vedas";
	   
	
	public void init(ServletConfig config) {
     System.out.println("My servlet has been initialized");
 }
	
	private static final long serialVersionUID = 1L;

 public Order() {
     super();
     // TODO Auto-generated constructor stub
 }

 Connection conn=null;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out=response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		String detailes=request.getParameter("orders");
		
		String  orderid = null ;
		String  mailid = null;
		String  paymenttype = null;
		String  totalcost = null;
		String  totaldiscount = null;
		String  netamount = null;
		String  timestamp = null;
		JSONArray item,daddress;
		String name =null, phone=null, address=null, landmark= null;
		String itemname = null; int price = 0 ; int quantity = 0 ;
		int rowinsert = 0;
		
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
			orderid = obj.getString("orderid");
			mailid = obj.getString("mailid");
			paymenttype = obj.getString("paymenttype");
			totalcost = obj.getString("totalcost");
			timestamp = obj.getString("timestamp");
			totaldiscount = obj.getString("totaldiscount");
			netamount = obj.getString("netamount");
			    	
			try{
				  daddress = obj.getJSONArray("deliveryaddress");
			      		final int n1 = daddress.length();
			      		for (int i2 = 0; i2 < n1; ++i2) {
			      		final JSONObject j1 = daddress.getJSONObject(i2);
			      		name = j1.getString("name");
			      		phone  = j1.getString("phone");
			      		address  = j1.getString("address");
			      		landmark = j1.getString("landmark");
			          }
			      	} catch (JSONException e1) {
						e1.printStackTrace();
						
					}
			      		
			      	try {
			      		String insertpin="INSERT INTO ORDERS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			      		PreparedStatement ps=conn.prepareStatement(insertpin);
			      		
			      		 try{
			      			 item = obj.getJSONArray("items");
			   			    final int n = item.length();
			   			    for (int i = 0; i < n; ++i) {
			   			    JSONObject j = null; j = item.getJSONObject(i);
			   			    
			   			       itemname = j.getString("itemname");
			   			       price    = j.getInt("price");
			   			       quantity   = j.getInt("quantity");
			   			       Double discount = j.getDouble("discount");
			   			      
			   			ps.setString(1,  orderid );
				      	ps.setString(2,  mailid);
			      		ps.setString(3,  itemname  );
			      		ps.setInt(4,  price);
			      		ps.setInt(5,  quantity );
			      		ps.setDouble(6, discount);
			      		ps.setString(7,  name );
			      		ps.setString(8,  phone);
			      		ps.setString(9,  address);
			      		ps.setString(10,  landmark);
			      		ps.setString(11,  paymenttype);
			      		ps.setString(12,  totaldiscount);
			      		ps.setString(13,  netamount);
			      		ps.setString(14,  totalcost);
			      		ps.setString(15,  timestamp);
			      	    int deliverystatus = 0;
						ps.setInt(16,  deliverystatus);
						System.out.println("total discount" +totaldiscount);
						System.out.println("Net amount" +netamount);
			      		rowinsert = ps.executeUpdate();
			      		
			      			}
			   			 if(rowinsert>0){
			 				
				      			JSONObject jsonobj = new JSONObject();
				      			String msg = "3";
				      			jsonobj.put("response", msg);
				      			out.println(jsonobj);
				      			
				      			try{
				      				String delete="DELETE FROM CART WHERE mailid=?";
						      		PreparedStatement pstmt=conn.prepareStatement(delete);
						      		pstmt.setString(1, mailid);
						      		int del = pstmt.executeUpdate();
						      		System.out.println("delete successfully.."+del);
						      		
				      			}catch(Exception e){
				      				e.printStackTrace();
				      			}
					
				      			}
				
				      		else{
				      			String e= "0" ;
				      			JSONObject jsonobj = new JSONObject();
				      			jsonobj.put("response", e);
				      			out.println(jsonobj);
				      		}
				   			    
			   			    
			   			    } catch (Exception e) {  
							 e.printStackTrace();
						    throw new RuntimeException(e);  
			      			}
			      		 
		   					}catch(SQLException e)
		   						{
		   							e.printStackTrace();
		   						}
			      	}
		catch(JSONException e)
			{
				e.printStackTrace();
				
			}
							
						if (conn != null) {
          
								try {
									conn.close();
								} catch (SQLException ex) {
									ex.printStackTrace();
								}
						}
		
		
	}
}
