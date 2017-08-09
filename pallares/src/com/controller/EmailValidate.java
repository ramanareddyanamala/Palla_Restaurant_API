package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailValidate {
	
	public static boolean checkUser(String mailid,String password) 
    {

	      boolean st =false;
	      Connection con=null;
	      
	      try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/palla", "root", "vedas");
			
			PreparedStatement ps =con.prepareStatement
                  ("select username,password from login where mailid=? and password=?");
			ps.setString(1, mailid);
			ps.setString(2, password);
			ResultSet rs =ps.executeQuery();
			st = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		return st;
	
    }
	
	public static boolean checkRegister(String mailid) 
    {

	      boolean st =false;
	      Connection con=null;
	      
	      try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/palla", "root", "vedas");
			
			PreparedStatement ps =con.prepareStatement
                  ("select username,password from registration where mailid=? ");
			ps.setString(1, mailid);
			
			ResultSet rs =ps.executeQuery();
			st = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		return st;
	
    }
	public static boolean checkFB(String mailid) 
    {

	      boolean st =false;
	      Connection con=null;
	      
	      try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/palla", "root", "vedas");
			
			PreparedStatement ps =con.prepareStatement
                ("select name,mailid from fb where mailid=? ");
			ps.setString(1, mailid);
			ResultSet rs =ps.executeQuery();
			st = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		return st;
	
    }
}
