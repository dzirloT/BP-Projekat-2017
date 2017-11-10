package com.etf.unsa.ba.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Korisnik {

		@Id
		@GeneratedValue
		private long id; 
		
		private String ime; 
		private String prezime; 
		private String mail; 
		private String password; 

		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getIme() {
			return ime;
		}
		public void setIme(String ime) {
			this.ime = ime;
		}
		public String getPrezime() {
			return prezime;
		}
		public void setPrezime(String prezime) {
			this.prezime = prezime;
		}
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
}
