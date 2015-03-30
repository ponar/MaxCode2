package com.jarek.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Uczynnosci {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String kod_czynnosc;
	private String zlecenie;
	private String nr_operacji;
	private String kod_stanowiska;
	private Date data_otw;
	private long godz_otw;
	private Date data_zak;
	private long godz_zak;
	private String kod_rcp;
	private String komentarz;
	private String formatDaty_otwarcia;
	private String formatDaty_zamkniecia;
	private String convertlongtoString_otwarcia;
	private String convertlongtoString_zamkniecia;
	private int il;

	public String getConvertlongtoString_zamkniecia() {
		long d = getGodz_zak();
		long powt = TimeUnit.SECONDS.toMillis(d);

		this.convertlongtoString_zamkniecia = new SimpleDateFormat("HH:mm:ss")
				.format(new Date(powt));
		if (this.convertlongtoString_zamkniecia.equals("01:00:00")) {
			return " ";
		}
		return this.convertlongtoString_zamkniecia;
	}

	public void setConvertlongtoString_zamkniecia(
			String convertlongtoString_zamkniecia) {
		this.convertlongtoString_zamkniecia = convertlongtoString_zamkniecia;
	}

	public String getConvertlongtoString_otwarcia() {
		long d = getGodz_otw();
		long powt = TimeUnit.SECONDS.toMillis(d);

		this.convertlongtoString_otwarcia = new SimpleDateFormat("HH:mm:ss")
				.format(new Date(powt));
		if (this.convertlongtoString_otwarcia.equals("01:00:00")) {
			return " ";
		}
		return this.convertlongtoString_otwarcia;
	}

	public void setConvertlongtoString_otwarcia(
			String convertlongtoString_otwarcia) {
		this.convertlongtoString_otwarcia = convertlongtoString_otwarcia;
	}

	public String getFormatDaty_zamkniecia() {
		Date zrodlo = null;
		if (getData_otw() == null) {
			return " ";
		}
		zrodlo = getData_zak();

		DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String zero = "1980.01.01";
		if (formatter.format(zrodlo).equals(zero)) {
			return " ";
		}
		return formatter.format(zrodlo);
	}

	public void setFormatDaty_zamkniecia(String formatDaty_zamkniecia) {
		this.formatDaty_zamkniecia = formatDaty_zamkniecia;
	}

	public String getFormatDaty_otwarcia() {
		Date zrodlo = null;
		if (getData_otw() == null) {
			return " ";
		}
		zrodlo = getData_otw();

		DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String zero = "1980.01.01";
		String zero2 = "1900.01.01";
		if ((formatter.format(zrodlo).equals(zero))
				|| (formatter.format(zrodlo).equals(zero2))) {
			return " ";
		}
		return formatter.format(zrodlo);
	}

	public void setFormatDaty_otwarcia(String formatDaty_otwarcia) {
		this.formatDaty_otwarcia = formatDaty_otwarcia;
	}

	public int getIl() {
		return this.il;
	}

	public void setIl(int il) {
		this.il = il;
	}

	public String getConvertlongtoString() {
		long d = getGodz_otw();
		long powt = TimeUnit.SECONDS.toMillis(d);

		this.convertlongtoString_otwarcia = new SimpleDateFormat("HH:mm:ss")
				.format(new Date(powt));
		return this.convertlongtoString_otwarcia;
	}

	public void setConvertlongtoString(String convertlongtoString) {
		this.convertlongtoString_otwarcia = convertlongtoString;
	}

	public String getFormatDaty() {
		Date zrodlo = null;
		if (getData_otw() == null) {
			return " ";
		}
		zrodlo = getData_otw();

		DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String zero = "1980.01.01";
		if (formatter.format(zrodlo).equals(zero)) {
			return " ";
		}
		return formatter.format(zrodlo);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKod_czynnosc() {
		return this.kod_czynnosc;
	}

	public void setKod_czynnosc(String kod_czynnosc) {
		this.kod_czynnosc = kod_czynnosc;
	}

	public String getZlecenie() {
		return this.zlecenie;
	}

	public void setZlecenie(String zlecenie) {
		this.zlecenie = zlecenie;
	}

	public String getNr_operacji() {
		return this.nr_operacji;
	}

	public void setNr_operacji(String nr_operacji) {
		this.nr_operacji = nr_operacji;
	}

	public String getKod_stanowiska() {
		return this.kod_stanowiska;
	}

	public void setKod_stanowiska(String kod_stanowiska) {
		this.kod_stanowiska = kod_stanowiska;
	}

	public Date getData_otw() {
		return this.data_otw;
	}

	public void setData_otw(Date data_otw) {
		this.data_otw = data_otw;
	}

	public long getGodz_otw() {
		return this.godz_otw;
	}

	public void setGodz_otw(long godz_otw) {
		this.godz_otw = godz_otw;
	}

	public Date getData_zak() {
		return this.data_zak;
	}

	public void setData_zak(Date data_zak) {
		this.data_zak = data_zak;
	}

	public long getGodz_zak() {
		return this.godz_zak;
	}

	public void setGodz_zak(long godz_zak) {
		this.godz_zak = godz_zak;
	}

	public String getKod_rcp() {
		return this.kod_rcp;
	}

	public void setKod_rcp(String kod_rcp) {
		this.kod_rcp = kod_rcp;
	}

	public String getKomentarz() {
		return this.komentarz;
	}

	public void setKomentarz(String komentarz) {
		this.komentarz = komentarz;
	}
}
