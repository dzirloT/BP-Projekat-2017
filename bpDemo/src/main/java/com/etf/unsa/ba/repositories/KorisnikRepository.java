package com.etf.unsa.ba.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.etf.unsa.ba.models.Korisnik;

public interface KorisnikRepository extends CrudRepository<Korisnik, Long>{
	Korisnik findFirstByMail(@Param("mail") String mail);
	
}
