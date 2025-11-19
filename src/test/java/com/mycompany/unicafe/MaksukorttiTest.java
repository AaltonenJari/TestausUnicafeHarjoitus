package com.mycompany.unicafe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class MaksukorttiTest {

    Maksukortti kortti;

    @BeforeEach
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    /*  Tee valmiiseen testiluokkaan MaksukorttiTest testit, jotka testaavat ainakin seuraavia asioita: */
	@Test
	public void kortinSaldoAlussaOikein() {
	    assertEquals("saldo: 0.10",  kortti.toString());
	}    

	@Test
	public void rahanLataaminenKasvattaaSaldoaOikein() {
	    kortti.lataaRahaa(25);
	    assertEquals("saldo: 0.35", kortti.toString());
	}
	
	@Test
	public void rahanOttaminenVahentaaSaldoaOikein() {
	    kortti.otaRahaa(5);
	    assertEquals("saldo: 0.5", kortti.toString());
	}

	@Test
	public void SaldoEiMuutuRahaaEiTarpeeksi() {
	    kortti.otaRahaa(20);
	    assertEquals("saldo: 0.10",  kortti.toString());
	}
	
    @Test
    public void otaRahaaPalauttaaTrueJosRahatRiittavat() {
        boolean onnistui = kortti.otaRahaa(5);

        // Testataan, että metodi palauttaa true
        assertTrue(onnistui, "Metodin pitäisi palauttaa true, kun saldo riittää");

        // Testataan, että saldo väheni oikein
        assertEquals("saldo: 0.5", kortti.toString());
    }

    @Test
    public void otaRahaaPalauttaaFalseJosRahatEivatRiita() {
        boolean onnistui = kortti.otaRahaa(20);

        // Testataan, että metodi palauttaa false
        assertFalse(onnistui, "Metodin pitäisi palauttaa false, kun saldo ei riitä");

        // Testataan, että saldo ei muuttunut
	    assertEquals("saldo: 0.10",  kortti.toString());
    }
}
