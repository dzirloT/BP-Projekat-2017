package com.etf.unsa.ba.controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;

import org.mockito.internal.util.collections.ArrayUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("jdbc")
@RestController
public class JdbcCtrl {
	
	Connection con;
	Connection oracleConn; 
	
	public JdbcCtrl(){
		
		try {
			con=DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/bpDemo","postgres","dbpass"
					);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		}
	}
	@RequestMapping("/getRowCount/{objectName}")
	public int getRowCount(@PathVariable String objectName)	{
		int rowCount = 0; 
		try {
			System.out.println("Servis je pozvan sa parametrom : " + objectName);
			Statement stmt = oracleConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as brojRedova FROM " + objectName);
			rs.next();
			rowCount = rs.getInt("brojRedova");
			System.out.println("Broj vracenih redova je ");
			System.out.println(rowCount);
			stmt.close();
			rs.close();
			
			 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
		return rowCount;
	}
	@RequestMapping("/getObjectNames/{objectName}")
	public ArrayList<String> getNames(@PathVariable String objectName)	{
		ArrayList<String> names = new ArrayList<String> (); 
		
		try {
			
			Statement stmt = oracleConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + objectName); 
			ResultSetMetaData meta = rs.getMetaData(); 
			String nazivKolone = meta.getColumnName(1);
			
			rs.close();
			stmt.close();
			stmt = oracleConn.createStatement();
			rs = stmt.executeQuery("SELECT " + nazivKolone + " FROM " + objectName); 
			while(rs.next())	{
				names.add(rs.getString(nazivKolone)); 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return names;
	}
	@RequestMapping("/getTableColumnNames")
	public ArrayList<String> getColumnNames()	{
		ArrayList<String> names = new ArrayList<String> (); 
		
		try {
			
			Statement stmt = oracleConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM  USER_TAB_COLUMNS"); 
			ResultSetMetaData meta = rs.getMetaData(); 
			String nazivTabele = meta.getColumnName(1);
			String nazivKolone = meta.getColumnName(2);
			
			rs.close();
			stmt.close();
			stmt = oracleConn.createStatement();
			rs = stmt.executeQuery("SELECT " +nazivTabele +", "+ nazivKolone + " FROM USER_TAB_COLUMNS"); 
			while(rs.next())	{
				names.add(rs.getString(nazivTabele)+"."+rs.getString(nazivKolone)); 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		System.out.println("Vracaju se imena");
		return names;
	}
	@RequestMapping("/getObjectsMetaData")
	public objectDescrtipion objectsMetaData(@RequestBody final objectDescName params)	{
		System.out.println(params.name);
		System.out.println(params.objectName);
		objectDescrtipion desc = new objectDescrtipion(); 
		desc.metaKeys = new ArrayList<String>(); 
		desc.metaValues = new ArrayList<String>(); 
		try {
			/*String connectionString = "jdbc:oracle:thin:@80.65.65.66:1521:ETFLAB";
			String userName = "BP07";
			String userPassword = "eyEeo2es";
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			oracleConn = DriverManager.getConnection(connectionString, userName, userPassword);*/
			Statement stmt = oracleConn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+params.objectName); 
			ResultSetMetaData meta = rs.getMetaData(); 
			String columnName = meta.getColumnName(1); 
			
			stmt.close();
			rs.close();
			 System.out.println("ojbectName je " + params.objectName);
			 System.out.println("name je " + params.name);
			 System.out.println("columnName je " + columnName);
			 stmt = oracleConn.createStatement(); 
			rs = stmt.executeQuery("SELECT * FROM " + params.objectName +
					" WHERE " + columnName + " = " + "'" + params.name + "'");
			meta = rs.getMetaData(); 
			int metaColumnCount = meta.getColumnCount();
			for(int i = 1; i <= metaColumnCount; i++)	{
				System.out.println(meta.getColumnName(i));
				desc.metaKeys.add(meta.getColumnName(i)); 
			}
			final int columnCount = meta.getColumnCount(); 
			System.out.println("Ide meta : ");
			//System.out.println(meta.toString());
			final List<List<String>> rowList = new LinkedList<List<String>>();
			
			while(rs.next())	{
				final List<String> columnList = new LinkedList<String>(); 
				rowList.add(columnList);
				
				for (int column = 1; column <= columnCount; ++column)	{
					
					final Object value = rs.getObject(column);
					desc.metaValues.add(String.valueOf(value)); 
					columnList.add(String.valueOf(value));
					
				}
			}
			rs.close();
			stmt.close();
			System.out.println("Kraj ispisa");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return desc; 
	}
	@RequestMapping("/createView")
	public dbConnectionResponse createView(@RequestBody final viewDesc params)	{
		System.out.println(params.title);
		System.out.println(params.tabele);
		System.out.println(params.kolone);
		System.out.println(params.uslov);
		dbConnectionResponse desc = new dbConnectionResponse();
		String sql= "CREATE VIEW "+ params.title +  " AS SELECT ";
		if (params.kolone.size()!=1) {
		for (int i=0; i<params.kolone.size()-1;i++) {
			sql+=params.kolone.get(i) +", ";
		}
		}
		sql+=params.kolone.get(params.kolone.size()-1);
		sql+=" FROM ";
		if (params.tabele.size()!=1) {
		for (int i=0; i<params.tabele.size()-1;i++) {
			sql+=params.tabele.get(i) +", ";
		}}
		sql+=params.tabele.get(params.tabele.size()-1);
		sql+= " WHERE " + params.uslov;
		System.out.println(sql);
		try {			
			int n = oracleConn.createStatement().executeUpdate(sql);
			System.out.println(Integer.toString(n)+"Kraj ispisa");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return desc; 
	}
	@RequestMapping("/createTrigger")
	public dbConnectionResponse createTrigger(@RequestBody final triggerDesc params)	{
		System.out.println(params.title);
		System.out.println(params.okidanje);
		System.out.println(params.akcija);
		System.out.println(params.table);
		System.out.println(params.variable);
		System.out.println(params.kod);
		System.out.println(params.red);
		dbConnectionResponse desc = new dbConnectionResponse();
		String sql= null;
		if (params.akcija.size()==1)
		{sql = "CREATE OR REPLACE TRIGGER \""+params.title+"\" "  + params.okidanje +
				" "+params.akcija.get(0) +" ON \""+params.table+"\" ";
			if (params.variable == null || params.variable.isEmpty())
			 sql = sql+ "BEGIN "+params.kod+" END;";
			else 
				 sql = sql+ "DECLARE " + params.variable +" BEGIN "+params.kod+" END;";}
		else if (params.akcija.size()==2)
		{sql = "CREATE OR REPLACE TRIGGER \""+params.title+"\" "  + params.okidanje +
			" "+params.akcija.get(0) +" OR "+ params.akcija.get(1)+ " ON \""+params.table+"\" ";
			if (params.variable == null || params.variable.isEmpty())
			 sql = sql+ "BEGIN "+params.kod+" END;";
			else 
				 sql = sql+ "DECLARE " + params.variable +" BEGIN "+params.kod+" END;";
		}
			else 
		{sql = "CREATE OR REPLACE TRIGGER \""+params.title+"\" "  + params.okidanje +
			" "+params.akcija.get(0) +" OR "+ params.akcija.get(1)+ " OR "+ params.akcija.get(2)+ " ON \""+params.table+"\" ";	
				if (params.variable == null || params.variable.isEmpty())
				 sql = sql+ "BEGIN "+params.kod+" END;";
				else 
					 sql = sql+ "DECLARE " + params.variable +" BEGIN "+params.kod+" END;";
		}
		System.out.println(sql);
		try {			
			int n = oracleConn.createStatement().executeUpdate(sql);
	
			System.out.println(Integer.toString(n)+"Kraj ispisa");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return desc; 
	}
	@RequestMapping("/establishConnection")
	public dbConnectionResponse establish (@RequestBody final dbConnectionParams params)	
		throws ServletException	{
		String connectionString = ""; 
		String userName = params.db_user_username;
		String userPassword = params.db_user_password;
		connectionString += ("jdbc:oracle:thin:@" + params.host + ":" + params.port + ":" + params.sid);
		dbConnectionResponse r = new dbConnectionResponse(); 
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			oracleConn = DriverManager.getConnection(connectionString, userName, userPassword); 
			
			Statement stmt = oracleConn.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT name from BP07.test"); 
			/*ResultSet testSet = stmt.executeQuery("Select * from user_tables"); 
			while(testSet.next())	{
				testSet.getCol
			}*/
			/*SELECT table_name from user_tables*/ /*select table_name from user_tables*/
			/*select trigger_name from user_triggers*/
			/*select view_name from user_views*/
			/*select 
			/*drugi dio table_name*/
			/*view_name*/
			/**/
			
			while(rs.next())	{
				String name = rs.getString("name"); 
				System.out.println("Name : " + name);
			}
			rs.close();
			stmt.close();
			//oracleConn.close();
			r.response = "Uspjeh"; 
			r.imeKorisnika = userName; 
			
			return r; 
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			r.response= e.getMessage(); 
			r.imeKorisnika = ""; 
			throw new ServletException(e.getMessage());
		}
		
	}
	@RequestMapping("/closeConnection")
	public void closeConnection()	{
		try {
			System.out.println("Konekcija se zatvara");
			oracleConn.close();
		} catch (Exception e) {
			// TODO: handle exception
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
	
	@RequestMapping("/getTableColumns/{objectName}")
	public ArrayList<String> getTableColumns(@PathVariable String objectName)	{
		ArrayList<String> columns = new ArrayList<String> (); 
		System.out.println("Vracam nazive tabela");
		
		try {
			
			Statement stmt = oracleConn.createStatement();
			System.out.println("1");
			
			String query = "select * from " + objectName;
			stmt = oracleConn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("2");
			ResultSetMetaData rsMetaData = rs.getMetaData();
			System.out.println("3");
			int numberOfColumns = rsMetaData.getColumnCount();

		    // get the column names; column indexes start from 1
		    for (int i = 1; i < numberOfColumns + 1; i++) {
		      String columnName = rsMetaData.getColumnName(i);
		      // Get the name of the column's table name
		      columns.add(columnName);
		      System.out.println("column name=" + columnName);
		    }
			stmt.close();
			stmt = oracleConn.createStatement();
			
		} catch (Exception e) {
			System.out.println("Greska " + e.getMessage());
			// TODO: handle exception
		}
		System.out.println("Vracam nazive kolona za tabelu " + objectName);
		return columns;
	}
	
	@RequestMapping("/createIndex")
	public dbConnectionResponse createIndex(@RequestBody final indexDesc params) {
		dbConnectionResponse desc = new dbConnectionResponse();
		String sql = null;
		
		sql = "CREATE INDEX " + params.title +
				" ON " + params.table + "(";
		
		for(int i = 0; i < params.columns.size(); i++) {
			System.out.println(params.columns.get(i));
			if (params.columns.size() - 1 != i) sql += params.columns.get(i) + ", ";
			else sql += params.columns.get(i) + ")";
		}
		
		try {
			oracleConn.createStatement().executeUpdate(sql);
			System.out.println("Kreiran je indeks.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		System.out.println(sql);
		
		return desc;
	}
	
	@RequestMapping("/showERD")
	public ArrayList<ERD> showERD() throws SQLException {
		System.out.println("Radi showERD");
		ArrayList<ERD> erd = new ArrayList<ERD>();
		try {
			String sql;
			
			sql = "SELECT utc.table_name tableName, utc.column_name columnName, "
				+ "    CASE "
				+ "    WHEN (utc.table_name, utc.column_name) IN ( "
				+ "        SELECT uc.table_name, ucc.column_name "
				+ "        FROM USER_CONSTRAINTS uc, USER_CONS_COLUMNS ucc "
				+ "        WHERE ucc.table_name = uc.table_name "
				+ "            AND ucc.constraint_name = uc.constraint_name "
				+ "            AND uc.constraint_type IN ('P', 'R')) THEN 'true' "
				+ "    ELSE 'false' END AS isKey "
				+ "FROM USER_TAB_COLUMNS utc, USER_OBJECTS uo "
				+ "WHERE uo.created >= TO_DATE('20171220', 'YYYYMMDD') "
				+ "    AND utc.table_name = uo.object_name " 
				+ "    AND uo.object_type = 'TABLE' "
				+ "ORDER BY utc.table_name ASC";

			
			ResultSet rs = oracleConn.createStatement().executeQuery(sql);
			System.out.println("Dobavio podatke.");
			rs.next();
			while(!rs.isAfterLast()) {
				erd.add(new ERD());
				//postavljanje varijable table name
				erd.get(erd.size() - 1).tableName = rs.getString("tableName");
				//varijabla za provjeru DO-WHILE uslova
				String lastTableName = erd.get(erd.size() - 1).tableName;
				
				do {
					if(rs.getString("isKey").equals("false"))
						erd.get(erd.size() - 1).columns.add(new ERDcolumn(rs.getString("columnName"), false));
					else
						erd.get(erd.size() - 1).columns.add(new ERDcolumn(rs.getString("columnName"), true));
					System.out.println(lastTableName + " " + rs.getString("columnName"));
				}while(rs.next() && rs.getString("tableName").equals(lastTableName));
				
				String sqlFK = "SELECT table_name tableName "
							 + "FROM USER_CONSTRAINTS "
							 + "WHERE constraint_type = 'R' "
							 + "	AND r_constraint_name IN( " 
							 + "		SELECT constraint_name "
							 + "		FROM ALL_CONSTRAINTS "
							 + "		WHERE table_name = '" + lastTableName + "' "
							 + "			AND constraint_type = 'P')";
				ResultSet rsFK = oracleConn.createStatement().executeQuery(sqlFK);
				while(rsFK.next()) {
					erd.get(erd.size() - 1).relations.add(new ERDrelation(rsFK.getString("tableName")));
				}
				rsFK.close();
			}
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Zavrsio showERD");
		return erd;
	}
	
	private static class ERD{
		public String tableName;
		public ArrayList<ERDcolumn> columns;
		public ArrayList<ERDrelation> relations;
		public ERD() {
			columns = new ArrayList<ERDcolumn>();
			relations = new ArrayList<ERDrelation>();
		}
	}
	private static class ERDcolumn{
		public String columnName;
		public boolean isKey; 
		
		public ERDcolumn(String columnName, boolean isKey){
			this.columnName = columnName;
			this.isKey = isKey;
		}
	}
	public static class ERDrelation{
		public String toTable;
		public ERDrelation(String toTable) {
			this.toTable = toTable;
		}
	}
	
	private static class objectDescName	{
		public String objectName;
		public String name; 
	}
	private static class objectDescrtipion	{
		public ArrayList<String> metaKeys; 
		public ArrayList<String> metaValues; 
	}
	private static class dbConnectionParams	{
		public String host; 
		public String port; 
		public String sid; 
		public String db_user_username; 
		public String db_user_password; 
	}
	private static class dbConnectionResponse	{
		public String response;
		public String imeKorisnika; 
	}
	private static class triggerDesc{
		public String title;
		public String okidanje;
		public ArrayList<String> akcija;
		public String table;
		public String variable;
		public String kod;
		public String red;
	}

	private static class indexDesc{
		public String title;
		public String table;
		public ArrayList<String> columns;
	}	
	
	private static class viewDesc{
		public String title;
		public ArrayList<String> tabele;
		public ArrayList<String> kolone;
		public String uslov;
	}
	
}
