package com.etf.unsa.ba.controllers;

import java.awt.PageAttributes.MediaType;
import java.lang.reflect.Array;
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

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@RequestMapping(value = "/primaryKeys", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody ArrayList<Relation> primaryKeys()	{
		ArrayList<Relation> relacije = new ArrayList<Relation>();
		
		try {
			DatabaseMetaData meta = oracleConn.getMetaData(); 
			ResultSet relevantTables = oracleConn.createStatement().executeQuery("SELECT object_name FROM all_objects WHERE object_type = 'TABLE' " 
					+ " AND created >= TO_DATE('20171220', 'YYYYMMDD') ");
			while(relevantTables.next())	{
				String tableName = relevantTables.getString(1); 
				ResultSet rel = meta.getPrimaryKeys("", "BP07", tableName); 
				if(rel.next())	{
					Relation relacija = new Relation(); 
					relacija.tableName = rel.getString(3);
					relacija.columnName = rel.getString(4); 
					
					relacije.add(relacija); 
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		for(int i = 0; i < relacije.size(); i++)
			System.out.println("Tabela *" + relacije.get(i).tableName + "* ima primary key constraint na koloni *" + relacije.get(i).columnName + "* w");
		return relacije;
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
	
	private void create_AutoIncrement_Trigger(String nazivTabele, String nazivKolone)	{
		String sequenceQuery = "CREATE SEQUENCE \"seq_" + nazivTabele + "_" + nazivKolone 
				+ "\" MINVALUE 1 START WITH 1 INCREMENT BY 1 CACHE 20";
		
		String triggerQuery = "CREATE OR REPLACE TRIGGER \"sequence_trigger_" 
				+ nazivTabele + "_" + nazivKolone + "\" BEFORE INSERT ON \"" + nazivTabele + "\" FOR EACH ROW BEGIN SELECT \"seq_"
				+ nazivTabele + "_" + nazivKolone + "\".NEXTVAL INTO :new.\"" + 
				nazivKolone + "\" FROM dual; END";
		try {
			oracleConn.createStatement().execute(sequenceQuery); 
			oracleConn.createStatement().execute(triggerQuery);
			
		} catch (Exception e) {
			System.out.println("Pada kreiranje auto incrementa");
			System.out.println(e.getMessage());
		}
	}
	@RequestMapping("/createTable")
	public String createTable(@RequestBody final Tabela tabela )	{

		boolean constraint = false; 
		ArrayList<Constraint> constraints = new ArrayList<Constraint>(); 
		String query = "CREATE TABLE \"" + tabela.name + "\" ( "; 
		for(int i = 0; i < tabela.columns.size(); i++)	{
			Kolona k = new Kolona ();
			k = tabela.columns.get(i); 
			
			if(k.keyType.equals("FOREIGN KEY"))
				constraint = true; 
			
			if(k.keyType.equals("FOREIGN KEY"))	{
				boolean exists = false ; 
				for(int j = 0; j < constraints.size(); j++) {
					if(constraints.get(j).primaryKeyTable.equals(k.relation.tableName))		{
						constraints.get(j).foreignKeyPart += ("\"" + k.columnName + "\" , "); 
						constraints.get(j).primaryKeyPart += ("\"" + k.relation.columnName + "\" , "); 
						exists = true; 
						break; 
					}
				}
				
				if(!exists)	{
					{
						Constraint c = new Constraint(); 
						c.primaryKeyTable = k.relation.tableName; 
						c.foreignKeyPart = "CONSTRAINT \"FK_" + tabela.name + "_" + k.relation.tableName 
								+ "\" FOREIGN KEY (\"" + k.columnName + "\", ";
						c.primaryKeyPart = " REFERENCES \"" + k.relation.tableName + "\"(\"" + k.relation.columnName + "\" , ";
						
						constraints.add(c); 
					}
				}
			}
			
			if(i != tabela.columns.size() - 1)	{
				query += ("\"" + k.columnName + "\" " + k.dataType);
				
				if(k.nullable && k.keyType.equals("none") && k.unique)	{
					if(k.dataType.equals("number"))
						query += "(10), "; 
					else if(k.dataType.equals("varchar2"))
						query += "(255), ";
					else if(k.dataType.equals("char"))
						query += "(10), ";
				}	else	{
						if(k.dataType.equals("number"))
							query += "(10) "; 
						else if(k.dataType.equals("varchar2"))
							query += "(255) ";
						else if(k.dataType.equals("char"))
							query += "(10) ";
						if(!k.nullable)	{
							if(k.keyType.equals("none") && !k.unique)
								query += " NOT NULL, ";
							else
								query += " NOT NULL "; 
						}
						if(k.unique)	{
							if(!k.keyType.equals("none"))
								query += " UNIQUE "; 
							else 
								query += " UNIQUE, ";
						}
						if(k.keyType.equals("PRIMARY KEY"))
							query += (" " + k.keyType + ", ");
						else 
							query += " ,"; 
				}
			}	else	{
					if(k.keyType.equals("FOREIGN KEY"))
						constraint = true;
					query += ("\"" + k.columnName + "\" " + k.dataType);
					
					if(k.dataType.equals("number"))
						query += "(10) "; 
					else if(k.dataType.equals("varchar2"))
								query += "(255) ";
					else if(k.dataType.equals("char"))
								query += "(10) ";
					if(!k.nullable)	
						query += " NOT NULL ";
					if(k.unique)
						query += " UNIQUE ";
					if(k.keyType.equals("PRIMARY KEY"))
						query += (" " + k.keyType + " ");
					if(constraint)
						query += ", "; 
				}
		}
		if(constraint)	{
			for(int i = 0; i < constraints.size(); i++)	{
					constraints.get(i).foreignKeyPart = new String(
							constraints.get(i).foreignKeyPart.substring(
									0, constraints.get(i).foreignKeyPart.length() - 2
									) + " ) "
							);
					constraints.get(i).primaryKeyPart = new String(
							constraints.get(i).primaryKeyPart.substring(
									0, constraints.get(i).primaryKeyPart.length() - 2
									) + " ) "
							);
					query += (constraints.get(i).foreignKeyPart + constraints.get(i).primaryKeyPart + " ");
					if(i != constraints.size() - 1)	
						query += " , "; 
					else 
						query += " "; 
						
			}
			for(int i = 0; i < constraints.size(); i++)	{
				System.out.println(constraints.get(i).foreignKeyPart + constraints.get(i).primaryKeyPart);
			}
		}
		query += ")";
		try {
			System.out.println(query);
			oracleConn.createStatement().execute(query);
			System.out.println("Tabela je kreirana");
			for(int i = 0; i < tabela.columns.size(); i++)	{
				Kolona k = new Kolona(); 
				k = tabela.columns.get(i); 
				if(k.autoIncrement && k.dataType.equals("number"))
					create_AutoIncrement_Trigger(tabela.name, k.columnName);
				System.out.println("Trigger za kolonu " + k.columnName + " je kreiran");
			}
			/*System.out.println("Brisemo sada malo...");
			
			oracleConn.createStatement().execute("DROP TABLE \"" + tabela.name + "\"");
			System.out.println("Tabela obrisana");
			
			for(int i = 0; i < tabela.columns.size(); i++)	{
				Kolona k = new Kolona(); 
				k = tabela.columns.get(i); 
				if(k.autoIncrement)	{
					oracleConn.createStatement().execute("DROP SEQUENCE \"seq_" + tabela.name + "_" + k.columnName + "\" ");
					System.out.println("Sekvenca obrisana");
				}
			}*/
			
		} catch (Exception e) {
			System.out.println("Pada funkcija za kreiranje tabele");
			System.out.println(e.getMessage());
		}
			
		return "Nije bitno sad"; 
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
	private static class Kolona	{
		public String columnName; 
		public String dataType; 
		public String keyType; 
		public Relation relation; 
		public boolean nullable; 
		public boolean unique; 
		public boolean autoIncrement; 
	}
	private static class Tabela	{
		public String name; 
		public ArrayList<Kolona> columns; 
	}
	private static class Relation	{
		public String tableName; 
		public String columnName;
	}
	private static class Constraint {
		public String primaryKeyTable; 
		public String foreignKeyPart; 
		public String primaryKeyPart; 
	}
}
