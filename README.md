# BP-Projekat-2017
## Java Spring
Da bi bilo moguće koristiti Java Spring framework, potrebno je prethodno imati instalirano okruženje za rad sa Javom (Java Runtime Environment). 
Na Linux operativnim sistemima je dovoljno kroz terminal koristiti komande za odgovarajući packet manager. Prije toga je potrebno dodati zvanični repozitorij sa kojeg će biti dobavljeni potrebni fajlovi. U nastavku će biti prikazan proces specifično za Ubuntu distribucije (kao i druge debian distribucije koje koriste APT)
Dodavanje repozitorija se vrši sljedećom komandom:

*sudo add-apt-repository ppa:webupd8team/java *

Instalacija Java okruženja se onda pokreće sljedećim komandama: 

*sudo apt-get install oracle-java8-installer 

sudo apt-get install oracle-java8-set-default*

Nakon što se odrade prethodni koraci, potrebno je pokrenuti Spring i importovati projekat (kao “existing maven project”), te desni klik na projekat i odabrati “build path” -> “add external archives…” i odabrati **“ojdbc8.jar”** iz projektnog foldera. 

## PostgreSQL
U ovom poglavlju će biti opisano postavljanje PostrgreSQL baze podataka. Za rad sa ovom bazom je potrebno instalirati PostgreSQL RDBMS (Relational Database Management System). Za jednostavniji rad je moguće instalirati grafički alat za rad s bazom podataka pod nazivom pgAdmin. Koraci za instalaciju ovih alata će biti prikazani u nastavku. 
Na Linux operativnim sistemima, koji koriste APT, je moguće instalaciju opisanih alata uraditi sljedećom komandom:
 *apt-get install postgresql postgresql-contrib postgresql-client pgadmin3 *
Nakon što se komanda izvrši, na sistemu su instalirani PostrgreSQL server i pgAdmin alat. Još je potrebno pokrenuti server i postaviti defaultnog korisnika za PostgreSQL proces, što se postiže sljedećim komandama: 
*sudo /etc/init.d/postgresql start 
sudo -u postgres psql postgres*

Nakon što se pokrene psql komanda, potrebno je odrediti password za datog korisnika, jer isti inicijalno nije postavljen, te ga treba postaviti na **“dbpass”**.
Nakon što se PostgreSQL instalira, potrebno je kreirati novu bazu i nazvati je **“bpDemo”**, te pod owner poljem staviti **“postgres”**. 
Nakon što odradimo sve prethodne korake, potrebno je run-at spring projekat i unutar web browsera unijeti sljedeci url : **“localhost:8080/#/”**

## Android 
U folderu Android se nalazi .apk file koji je potrebno instalirati na odgovarajućem Android uređaju.
