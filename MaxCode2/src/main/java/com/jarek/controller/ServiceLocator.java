package com.jarek.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
//import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.jarek.model.*;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

import com.jarek.utils.HibernateUtil;

@ManagedBean(name = "serviceLocator")
@SessionScoped
public class ServiceLocator implements Serializable {

	private String kod_rcp;
	private String stanowisko;
	private String zlecenie;
	private String komentarz;
	private List<Uczynnosci> lista = new ArrayList();
	private String moj_wybor;
	private String moj_wybor_przerwy;
	private String wyjscie;
	private boolean rezultat;
	private List<Mford> wyjsciowa_ilosc;
	private int input_user;
	private boolean wynik;
	private boolean czy_koniec;
	private String pracownik;
	private String index;
	private String panel;
	//
	private boolean komunikat1;
	private boolean komunikat2;
	//
	private String message;

	private int suma_wykona;

	public int getSuma_wykona() {
		return suma_wykona;
	}

	public void setSuma_wykona(int suma_wykona) {
		this.suma_wykona = suma_wykona;
	}

	//
	public void suma_wykon(String zlecenie, String operacja) {
		System.out.println("Zlecenie: " + zlecenie + " Operacja: " + operacja);
		String zlec = zlecenie.trim();
		String oper = operacja.trim();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		long sumSalary = 0;
		String kod = "S";
		try {
			transaction = session.beginTransaction();
			Query query1 = session
					.createQuery("select sum(il) from Uczynnosci where zlecenie =:zlec and nr_operacji =:oper and kod_czynnosc =:kod_cz ");

			sumSalary = (Long) query1.setString("zlec", zlec)
					.setString("oper", oper).setString("kod_cz", kod)
					.uniqueResult();
		} catch (HibernateException localHibernateException) {
		}
		session.close();
		System.out.println("Ilosc :" + sumSalary);
		if (sumSalary > 0)
			setSuma_wykona(new BigDecimal(sumSalary).intValueExact());

	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String klik;

	public boolean isKomunikat1() {
		return komunikat1;
	}

	public void setKomunikat1(boolean komunikat1) {
		this.komunikat1 = komunikat1;
	}

	public boolean isKomunikat2() {
		return komunikat2;
	}

	public void setKomunikat2(boolean komunikat2) {
		this.komunikat2 = komunikat2;
	}

	public String getKlik() {
		return klik;
	}

	public void setKlik(String klik) {
		this.klik = klik;
	}

	//
	public boolean isWynik() {
		return wynik;
	}

	public boolean isCzy_koniec() {
		return czy_koniec;
	}

	public void setCzy_koniec(boolean czy_koniec) {
		this.czy_koniec = czy_koniec;
	}

	public String getKod_rcp() {
		return kod_rcp;
	}

	public void setKod_rcp(String kod_rcp) {
		this.kod_rcp = kod_rcp;
	}

	public String getStanowisko() {
		return stanowisko;
	}

	public void setStanowisko(String stanowisko) {
		this.stanowisko = stanowisko;
	}

	public String getZlecenie() {
		return zlecenie;
	}

	public void setZlecenie(String zlecenie) {
		this.zlecenie = zlecenie;
	}

	public String getKomentarz() {
		return komentarz;
	}

	public void setKomentarz(String komentarz) {
		this.komentarz = komentarz;
	}

	public List<Uczynnosci> getLista() {
		return lista;
	}

	public void setLista(List<Uczynnosci> lista) {
		this.lista = lista;
	}

	public String getMoj_wybor() {
		return moj_wybor;
	}

	public void setMoj_wybor(String moj_wybor) {
		this.moj_wybor = moj_wybor;
	}

	public String getMoj_wybor_przerwy() {
		return moj_wybor_przerwy;
	}

	public void setMoj_wybor_przerwy(String moj_wybor_przerwy) {
		this.moj_wybor_przerwy = moj_wybor_przerwy;
	}

	public String getWyjscie() {
		return wyjscie;
	}

	public void setWyjscie(String wyjscie) {
		this.wyjscie = wyjscie;
	}

	public boolean isRezultat() {
		return rezultat;
	}

	public void setRezultat(boolean rezultat) {
		this.rezultat = rezultat;
	}

	public List<Mford> getWyjsciowa_ilosc() {
		return wyjsciowa_ilosc;
	}

	public void setWyjsciowa_ilosc(List<Mford> wyjsciowa_ilosc) {
		this.wyjsciowa_ilosc = wyjsciowa_ilosc;
	}

	public int getInput_user() {
		return input_user;
	}

	public void setInput_user(int input_user) {
		this.input_user = input_user;
	}

	public void setWynik(boolean wynik) {
		this.wynik = wynik;
	}

	public String getPracownik() {
		return pracownik;
	}

	public void setPracownik(String pracownik) {
		this.pracownik = pracownik;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getPanel() {
		return panel;
	}

	public void setPanel(String panel) {
		this.panel = panel;
	}

	// zapis kodu RCP???
	public void run() throws IOException, ParseException {
		String kod = getKod_rcp();
		int rows = sprawdzenie_zasobu(kod);
		if (rows > 0) {
			// System.out.println("Kod RCP: " + kod + " Stanowisko: " +
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			String format = df.format(new Date());
			DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
			long start = System.currentTimeMillis();
			long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(start);
			Date today = df2.parse(format);
			long data_zero = 315529200000L;
			Date zero = new Date(data_zero);
			Uczynnosci czynnosci = new Uczynnosci();
			czynnosci.setKod_stanowiska(this.stanowisko);
			czynnosci.setKod_rcp(kod);
			czynnosci.setKod_czynnosc("I");
			czynnosci.setData_otw(today);
			czynnosci.setGodz_otw(timeSeconds);
			czynnosci.setData_zak(zero);
			czynnosci.setNr_operacji(null);
			czynnosci.setZlecenie("");
			czynnosci.setId(0);
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				session.save(czynnosci);
				transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
					e.printStackTrace();
				}
			} finally {
				session.close();
			}

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("panel.xhtml");

		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("blad.xhtml");
		}

	}

	public int sprawdzenie_zasobu(String zasob) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int count = 0;
		transaction = session.beginTransaction();
		Query query = session
				.createQuery("from Mmres where mmres_resid like '%" + zasob
						+ "%'");
		for (Iterator iterator = query.iterate(); iterator.hasNext();) {
			iterator.next();
			count++;
		}
		session.close();
		return count;
	}

	public static Flash flashScope() {
		return (FacesContext.getCurrentInstance().getExternalContext()
				.getFlash());
	}

	// Uruchomienie zlecenia A
	public String uruchamianie() throws ParseException, IOException {
		String kod = getKod_rcp();
		String stanowisko = getStanowisko();
		int spacePos = getZlecenie().indexOf(" ");
		int konPos = getZlecenie().lastIndexOf(" ");
		String zlecenie = getZlecenie().substring(0, spacePos);
		String operacja = getZlecenie().substring(konPos + 1,
				getZlecenie().length());
		
		setWynik(status_zlecenia(zlecenie, operacja, "A"));
		// albo zlecenie zakonczone D to nie mozna uruchomic
		setCzy_koniec(status_zlecenia(zlecenie, operacja, "D"));// czy koniec
		// true zwroci 0 ; false wiecej rekordow
		// jesli 0 to operacja nie zakonczona
		if (isWynik() & isCzy_koniec()) {
			System.out.println("Zlecenie sie uruchamia");

			RequestContext.getCurrentInstance().execute(
					"PF('acceptedDialog0').show()");
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			String format = df.format(new Date());
			DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
			long start = System.currentTimeMillis();
			long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(start);
			Date today = df2.parse(format);
			long format0 = 315529200000L;
			Date zero = new Date(format0);
			Uczynnosci czynnosci = new Uczynnosci();
			czynnosci.setKod_stanowiska(stanowisko);
			czynnosci.setKod_rcp(kod);
			czynnosci.setKod_czynnosc("A");
			czynnosci.setData_otw(today);
			czynnosci.setGodz_otw(timeSeconds);
			czynnosci.setData_zak(zero);
			czynnosci.setNr_operacji(operacja);
			czynnosci.setZlecenie(zlecenie);
			czynnosci.setIl(0);
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				session.save(czynnosci);
				transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
					e.printStackTrace();
				}
			} finally {
				session.close();
			}
		} else {
			System.out.println("Zlecenie zosta³o ju¿ uruchomione");
			RequestContext.getCurrentInstance().execute(
					"PF('acceptedDialog1').show()");

		}
		reset();
		return "index?faces-redirect=true";

	}

	// sprawdzenie zlecenia jesli jest juz uruchomione nie uruchomi sie ponownie
	public boolean status_zlecenia(String zlec, String operacja, String kod) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int count = 0;
		transaction = session.beginTransaction();
		Query query = session
				.createQuery("from Uczynnosci where zlecenie =:zlec and nr_operacji =:operacja and kod_czynnosc =:kod ");
		Iterator iterator = query.setString("zlec", zlec)
				.setString("operacja", operacja).setString("kod", kod)
				.iterate();
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		session.close();
		if (count == 0) {
			return true;// zlecenie mozna uruchamiac
		}
		return false; // zlecenie nie mozna uruchamiac
	}

	//
	public void na_poczatek() throws IOException {
		System.out.println("Klik");
		setKod_rcp("");
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index.xhtml");
	}

	public void reset() throws IOException {
		System.out.println("Reset!");
		setKod_rcp("");
		setStanowisko("");
		setZlecenie("");
		setKomentarz("");

	}

	/*
	 * Metoda przerwania zlecenia
	 */
	public String przerwane_zlecenie() throws IOException, ParseException {
		String kod = getKod_rcp();
		String stanowisko = getStanowisko();
		// pobranie zlecenie i oper
		int specePos = getZlecenie().indexOf(" ");
		int konPos = getZlecenie().lastIndexOf(" ");
		String zlecenie = getZlecenie().substring(0, specePos);
		String operacja = getZlecenie().substring(konPos + 1,
				getZlecenie().length());
		System.out.println("Zlecenie wejsciowe: " + zlecenie + " Operacja: "
				+ operacja);

		if ((zlecenie.equals("ZPP0040515") & operacja.equals("60"))) {
			System.out.println("Odblokowanie zlecenia!");
			pod_przerwanie_zlecenia();
			reset();
			RequestContext.getCurrentInstance().execute(
					"PF('odblokowanie').show()");

			return "index?faces-redirect=true";
		}
		boolean wynik1 = status_zlecenia(zlecenie, operacja, "A");

		if (wynik1) {
			System.out.println("Zlecenia nie mo¿na przerwaæ");
			RequestContext.getCurrentInstance().execute(
					"PF('nieprzerwaDialog').show()");
		} else {
			System.out.println("Zlecenie mo¿na przerwaæ");
			pod_przerwanie_zlecenia();
			RequestContext.getCurrentInstance().execute(
					"PF('przerwaDialog').show()");

		}
		reset();
		return "index?faces-redirect=true";

	}

	/*
	 * sprawdzenie kolejnego warunku przerwania
	 */
	public void pod_przerwanie_zlecenia() throws ParseException, IOException {
		String kod = getKod_rcp();
		System.out.println("KOd RCP: " + kod);
		String stanowisko = getStanowisko();
		//
		int spacePos = getZlecenie().indexOf(" ");
		int konPos = getZlecenie().lastIndexOf(" ");
		String zlecenie = getZlecenie().substring(0, spacePos);
		String operacja = getZlecenie().substring(konPos + 1,
				getZlecenie().length());
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		String format = df.format(new Date());
		DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
		long start = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(start);
		Date today = df2.parse(format);

		long format0 = 315529200000L;
		Date zero = new Date(format0);
		System.out.println("Komentarz: " + getMoj_wybor_przerwy());
		Uczynnosci czynnosci = new Uczynnosci();
		czynnosci.setKod_stanowiska(stanowisko);

		czynnosci.setKod_rcp(kod);
		czynnosci.setKod_czynnosc("B");

		czynnosci.setData_zak(today);
		czynnosci.setGodz_zak(timeSeconds);
		czynnosci.setData_otw(zero);
		czynnosci.setIl(0);
		czynnosci.setNr_operacji(operacja);
		czynnosci.setZlecenie(zlecenie);

		czynnosci.setKomentarz(getMoj_wybor_przerwy());
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(czynnosci);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				e.printStackTrace();
			}
		} finally {
			session.close();
		}

	}

	// kontynuacja zlecenia
	public String kontynuacja() throws IOException, ParseException {
		String kod = getKod_rcp();
		String stanowisko = getStanowisko();
		int spacePos = getZlecenie().indexOf(" ");
		int konPos = getZlecenie().lastIndexOf(" ");
		String zlecenie = getZlecenie().substring(0, spacePos);
		String operacja = getZlecenie().substring(konPos + 1,
				getZlecenie().length());

		boolean wynik1 = sprawdzenie_zlecenia(zlecenie, operacja, "B");
		if (wynik1) {
			System.out.println("Zlecenie nie zosta³o zatrzymane!!!");
			RequestContext.getCurrentInstance().execute(
					"PF('stopDialog1').show()");
		} else {
			System.out.println("Zlecenie mo¿na kontynuowaæ!");
			RequestContext.getCurrentInstance().execute(
					"PF('nonstopDialog0').show()");
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			String format = df.format(new Date());
			DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
			long start = System.currentTimeMillis();
			long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(start);
			Date today = df2.parse(format);
			long format0 = 315529200000L;
			Date zero = new Date(format0);
			Uczynnosci czynnosci = new Uczynnosci();
			czynnosci.setKod_stanowiska(stanowisko);
			czynnosci.setKod_rcp(kod);
			czynnosci.setKod_czynnosc("C");
			czynnosci.setData_otw(today);
			czynnosci.setGodz_otw(timeSeconds);
			czynnosci.setData_zak(zero);
			czynnosci.setNr_operacji(operacja);
			czynnosci.setZlecenie(zlecenie);
			czynnosci.setIl(0);
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				session.save(czynnosci);
				transaction.commit();
				setWynik(true);
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
					e.printStackTrace();
				}
			} finally {
				session.close();
			}

		}
		reset();
		return "index?faces-redirect=true";

	}

	//
	public void powrot() throws IOException {
		System.out.println("Powrót do strony g³ównej");
		setKod_rcp("");
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index.xhtml");
	}

	//
	public boolean sprawdzenie_zlecenia(String zlec, String operacja, String kod) {
		System.out.println("Zlecenie : " + zlec + " Kod operacji: " + operacja
				+ " Kod: " + kod);

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int count = 0;
		transaction = session.beginTransaction();
		Query query = session
				.createQuery("from Uczynnosci where zlecenie =:zlec and nr_operacji =:operacja and kod_czynnosc =:kod  ");
		Iterator iterator = query.setString("zlec", zlec)
				.setString("operacja", operacja).setString("kod", kod)
				.iterate();
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		session.close();
		if (count == 0) {
			return true;
		}
		return false;
	}

	// ilosc w zleceniu
	public void ilosc() {
		System.out.print("ILOSC");
		String kod = getKod_rcp();
		String stanowisko = getStanowisko();
		int spacePos = getZlecenie().indexOf(" ");
		int konPos = getZlecenie().lastIndexOf(" ");
		String zlecenie = getZlecenie().substring(0, spacePos);
		String operacja = getZlecenie().substring(konPos + 1,
				getZlecenie().length());
		// test
		suma_wykon(zlecenie, operacja);
		//
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session
				.createQuery(" from Mford where mford_orderno =:zlec ");
		this.wyjsciowa_ilosc = query.setString("zlec", zlecenie).list();// ?
		session.getTransaction().commit();
		session.close();
	}

	//
	public void wprowadz() throws ParseException, IOException {
		int wpis = getInput_user();
		String kod = getKod_rcp();
		String stanowisko = getStanowisko();
		int spacePos = getZlecenie().indexOf(" ");
		int konPos = getZlecenie().lastIndexOf(" ");
		String zlecenie = getZlecenie().substring(0, spacePos);
		String operacja = getZlecenie().substring(konPos + 1,
				getZlecenie().length());

		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		String format = df.format(new Date());
		DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
		long start = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(start);
		Date today = df2.parse(format);

		Uczynnosci uczynnosci = new Uczynnosci();
		uczynnosci.setKod_rcp(kod);
		uczynnosci.setKod_stanowiska(stanowisko);
		uczynnosci.setKod_czynnosc("S");
		uczynnosci.setData_zak(today);
		uczynnosci.setGodz_otw(timeSeconds);
		uczynnosci.setIl(wpis);
		uczynnosci.setData_otw(null);
		uczynnosci.setZlecenie(zlecenie);
		uczynnosci.setNr_operacji(operacja);

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(uczynnosci);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		this.wyjsciowa_ilosc.clear();
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index.xhtml");
	}

	//
	public String zakonczenie() throws IOException, ParseException {
		System.out.println("Zakonczenie operacji");
		String kod = getKod_rcp();
		String stanowisko = getStanowisko();
		int spacePos = getZlecenie().indexOf(" ");
		int konPos = getZlecenie().lastIndexOf(" ");
		String zlecenie = getZlecenie().substring(0, spacePos);
		String operacja = getZlecenie().substring(konPos + 1,
				getZlecenie().length());
		boolean wynik = sprawdzenie_zlecenia(zlecenie, operacja, "A");
		if (wynik) {
			System.out.println("Zlecenie nie zakoñczone!!!");
			RequestContext.getCurrentInstance().execute(
					"PF('niekoniecDialog1').show()");
		} else {
			boolean wynik2 = sprawdzenie_zlecenia(zlecenie, operacja, "B");
			// System.out.println("Zosta³o ju¿ ponownie zatrzymane: " + wynik2);
			if (!wynik2) {
				System.out.println("Zlecenie zakoñczone!!!");
				RequestContext.getCurrentInstance().execute(
						"PF('koniecDialog0').show()");
				SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				String format = df.format(new Date());
				DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
				long start = System.currentTimeMillis();
				long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(start);
				Date today = df2.parse(format);

				long format0 = 315529200000L;
				Date zero = new Date(format0);
				Uczynnosci czynnosci = new Uczynnosci();
				czynnosci.setKod_stanowiska(stanowisko);
				czynnosci.setKod_rcp(kod);
				czynnosci.setKod_czynnosc("D");
				czynnosci.setData_otw(zero);
				czynnosci.setGodz_otw(0L);
				czynnosci.setData_zak(today);
				czynnosci.setGodz_zak(timeSeconds);
				czynnosci.setNr_operacji(operacja);
				czynnosci.setZlecenie(zlecenie);
				czynnosci.setIl(0);
				Session session = HibernateUtil.getSessionFactory()
						.openSession();
				Transaction transaction = null;
				try {
					transaction = session.beginTransaction();
					session.save(czynnosci);
					transaction.commit();
				} catch (Exception e) {
					if (transaction != null) {
						transaction.rollback();
						e.printStackTrace();
					}
				} finally {
					session.close();
				}
			}

		}
		reset();
		return "index?faces-redirect=true";

	}

	// sprawdza historie zlecenia i operacji
	public List<Uczynnosci> wyswietla() {
		String wejscie = getZlecenie();
		if (wejscie.isEmpty()) {
			return null;
		}
		String zle = wejscie.substring(0, wejscie.indexOf(" "));

		String oper_wej = wejscie.substring(wejscie.lastIndexOf(" "),
				wejscie.length());
		int oper = Integer.parseInt(oper_wej.trim());
		System.out
				.println("Badane zlecenie: " + zle + " Operacja: " + oper_wej);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			Query query1 = session
					.createQuery("from Uczynnosci where zlecenie =:zlec and nr_operacji =:oper order by id");
			this.lista = query1.setString("zlec", zle).setInteger("oper", oper)
					.list();
		} catch (HibernateException localHibernateException) {
		}
		session.close();

		return this.lista;
	}

	//
	public void czyszczenie() {
		setZlecenie("");
		this.lista.clear();

	}

	//
	public void postoj() throws IOException, ParseException {
		System.out.println("Postoj");
		String kod = getKod_rcp();
		String stanowisko = getStanowisko();
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
		long start = System.currentTimeMillis();
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(start);
		String format = df.format(new Date());
		Date today = df2.parse(format);
		String przyczyna = getMoj_wybor();
		String moj_pracownik = getPracownik();
		Uczynnosci czynnosci = new Uczynnosci();
		czynnosci.setKod_stanowiska(stanowisko);
		czynnosci.setKod_rcp(kod);
		czynnosci.setKod_czynnosc("P");
		czynnosci.setData_otw(today);
		czynnosci.setGodz_otw(timeSeconds);
		czynnosci.setData_zak(null);
		czynnosci.setGodz_zak(0L);
		czynnosci.setKomentarz(przyczyna + " " + moj_pracownik);
		czynnosci.setNr_operacji(null);
		czynnosci.setZlecenie(this.zlecenie);

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(czynnosci);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				e.printStackTrace();
			}
		} finally {
			session.close();
		}
		reset();		
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index.xhtml");
	}

	// metoda wyjscia
	public void exit() throws IOException {
		setKod_rcp("");
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index.xhtml");
	}

}
