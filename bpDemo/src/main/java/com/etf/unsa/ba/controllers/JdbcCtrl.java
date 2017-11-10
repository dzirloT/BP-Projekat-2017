package com.etf.unsa.ba.controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("jdbc")
@RestController
public class JdbcCtrl {
	
	Connection con;
	
	public JdbcCtrl(){
		
		try {
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/bpDemo","postgres","dbpass");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		}
	}
	
	@RequestMapping("/meta")
	public void metadata(){
		try {
			Statement s=con.createStatement();
			DatabaseMetaData md=con.getMetaData();
			ResultSet rsmdt=md.getTables("", "", "%", null);
			ResultSet rs=s.executeQuery("select * from korisnik;");
			
			while(rs.next()){
				System.out.println(rs.getString(3)+" "+rs.getString(3));
			}
			
			while(rsmdt.next()){
				System.out.println(rsmdt.getString(3));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
