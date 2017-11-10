package com.etf.unsa.ba.controllers;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etf.unsa.ba.models.Korisnik;
import com.etf.unsa.ba.repositories.KorisnikRepository;

@RestController
@RequestMapping("/korisnici")
public class KorisnikController extends BaseController<Korisnik, KorisnikRepository>{
	
	@RequestMapping("byMail/{id}")
	public Korisnik byMail(@PathVariable long id)	{
		return this.repository.findOne(id); 
	}
	
	@RequestMapping("login")
	public Korisnik login(@RequestBody final userLoginParams params) 
		throws ServletException	{
		//System.out.println(params.korisnickiMail);
		//System.out.println(params.korisnickiPassword);
		if(this.repository.findFirstByMail(params.korisnickiMail) == null )	{
			//System.out.println("NEMA TOG MAILA ! ! ! ! ");
			throw new ServletException("Email"); 
		}
		//System.out.println("Password korisnika sa tim mailom je: " + this.repository.findFirstByMail(params.korisnickiMail).getPassword());
		if(!this.repository.findFirstByMail(params.korisnickiMail).getPassword().equals(params.korisnickiPassword))
			throw new ServletException("Password");
		return this.repository.findFirstByMail(params.korisnickiMail); 
	}
	
	@RequestMapping("register")
	public boolean register (@RequestBody final userRegistrationParams params)
		throws ServletException	{
		if(this.repository.findFirstByMail(params.korisnickiMail) != null){
			System.out.println("Primio je sljedece parametre : " + params.korisnickiMail);
			System.out.println("Sta li cu vratit...   " + this.repository.findFirstByMail(params.korisnickiMail));
			System.out.println("JEBO MATER SVOJU STA SE DESAVA....");
			System.out.println("Ime je: ---- " + this.repository.findFirstByMail(params.korisnickiMail).getIme() + "----");
			throw new ServletException("Mail");
			
		}
			 
		
		Korisnik k = new Korisnik ();
		k.setIme(params.imeKorisnika); 
		k.setPrezime(params.prezimeKorisnika);
		k.setMail(params.korisnickiMail); 
		k.setPassword(params.korisnickiPassword);
		
		this.repository.save(k); 
		
		return true; 
	}
	
	private static class userLoginParams	{
		public String korisnickiMail; 
		public String korisnickiPassword; 
	}
	
	private static class userRegistrationParams	{
		public String imeKorisnika; 
		public String prezimeKorisnika; 
		public String korisnickiMail; 
		public String korisnickiPassword; 
	}
}
