package com.mycompany.unicafe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class KassapaateTest {
    Kassapaate paate;
    /* Tehdään testiluokka KassapaateTest ja siihen testit, jotka testaavat ainakin seuraavia asioita: */

    @BeforeEach
    public void setUp() {
    	paate = new Kassapaate();
    }

    /*  luodun kassapäätteen rahamäärä ja myytyjen lounaiden määrä on oikea (rahaa 1000, lounaita myyty 0) */
	@Test
	public void paatteenSaldoAlussaOikein() {
	    assertEquals(100000, paate.kassassaRahaa());
		int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
	    assertEquals(0,  myydytLounaat);
	    
	}

    /*  käteisosto toimii sekä edullisten että maukkaiden lounaiden osalta
            jos maksu riittävä: kassassa oleva rahamäärä kasvaa lounaan hinnalla ja vaihtorahan suuruus on oikea
	        jos maksu on riittävä: myytyjen lounaiden määrä kasvaa */
	@Test
	public void kateisostoEdullinenLounasToimiiOikein() {
		int vaihtoraha = paate.syoEdullisesti(500);
		int kassassaRahaa = paate.kassassaRahaa();
		assertEquals(100240, kassassaRahaa);
		assertEquals(260, vaihtoraha);
		int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
		assertEquals(1, myydytLounaat);
	}	
	
	@Test
	public void kateisostoMaukasLounasToimiiOikein() {
		int vaihtoraha = paate.syoMaukkaasti(500);
		int kassassaRahaa = paate.kassassaRahaa();
		assertEquals(100400, kassassaRahaa);
		assertEquals(100, vaihtoraha);
		int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
		assertEquals(1, myydytLounaat);
	}	

	/* jos maksu ei ole riittävä: kassassa oleva rahamäärä ei muutu, kaikki rahat palautetaan vaihtorahana ja myytyjen lounaiden määrässä ei muutosta */
	@Test
	public void kateisostoEdullinenLounasRahaEiRiitaToimiiOikein() {
		int vaihtoraha = paate.syoEdullisesti(200);
		int kassassaRahaa = paate.kassassaRahaa();
		assertEquals(100000, kassassaRahaa);
		assertEquals(200, vaihtoraha);
		int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
		assertEquals(0, myydytLounaat);
	}	
	
	@Test
	public void kateisostoMaukasLounasRahaEiRiitaToimiiOikein() {
		int vaihtoraha = paate.syoMaukkaasti(200);
		int kassassaRahaa = paate.kassassaRahaa();
		assertEquals(100000, kassassaRahaa);
		assertEquals(200, vaihtoraha);
		int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
		assertEquals(0, myydytLounaat);
	}
	
    /*  seuraavissa testeissä tarvitaan myös Maksukorttia jonka oletetaan toimivan oikein */
    /*  korttiosto toimii sekä edullisten että maukkaiden lounaiden osalta */
	@Test
	public void korttiostoEdullinenLounasToimiiOikein() {
		Maksukortti kortti = new Maksukortti(500);
        boolean onnistui = paate.syoEdullisesti(kortti);

        // Testataan, että metodi palauttaa true
        assertTrue(onnistui, "Metodin pitäisi palauttaa true, kun saldo riittää");

		if (onnistui) {
            /* jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true */
		    assertEquals(260, kortti.saldo());

		    /* jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa */ 
		    int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
		    assertEquals(1, myydytLounaat);
	    }	
	}

	@Test
	public void korttiostoMaukasLounasToimiiOikein() {
		Maksukortti kortti = new Maksukortti(500);
        boolean onnistui = paate.syoMaukkaasti(kortti);

        // Testataan, että metodi palauttaa true
        assertTrue(onnistui, "Metodin pitäisi palauttaa true, kun saldo riittää");

		if (onnistui) {
            /* jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true */
		    assertEquals(100, kortti.saldo());

		    /* jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa */ 
		    int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
		    assertEquals(1, myydytLounaat);
	    }	
	}

	@Test
	public void korttiostoEdullinenLounasRahaEiRiitaToimiiOikein() {
		Maksukortti kortti = new Maksukortti(200);
        boolean onnistui = paate.syoEdullisesti(kortti);

        // Testataan, että metodi palauttaa false
        assertFalse(onnistui, "Metodin pitäisi palauttaa false, kun saldo ei riitä");
		if (!onnistui) {
            /* jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen lounaiden määrä muuttumaton ja palautetaan false */
			assertEquals(200,  kortti.saldo());
		    int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
		    assertEquals(0, myydytLounaat);

		    /*  kassassa oleva rahamäärä ei muutu kortilla ostettaessa */ 
		    assertEquals(100000, paate.kassassaRahaa());
	    }	
	}

	@Test
	public void korttiostoMaukasLounasRahaEiRiitaToimiiOikein() {
		Maksukortti kortti = new Maksukortti(200);
        boolean onnistui = paate.syoMaukkaasti(kortti);

        // Testataan, että metodi palauttaa false
        assertFalse(onnistui, "Metodin pitäisi palauttaa false, kun saldo ei riitä");
		if (!onnistui) {
            /* jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen lounaiden määrä muuttumaton ja palautetaan false */
			assertEquals(200,  kortti.saldo());
		    int myydytLounaat = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
		    assertEquals(0, myydytLounaat);

		    /*  kassassa oleva rahamäärä ei muutu kortilla ostettaessa */ 
		    assertEquals(100000, paate.kassassaRahaa());
	    }
	}

	@Test
	public void kortinLatausToimiiOikein() {
		Maksukortti kortti = new Maksukortti(200);
		paate.lataaRahaaKortille(kortti, 300);
		
		/*  kortille rahaa ladattaessa kortin saldo muuttuu */ 
		assertEquals(500,  kortti.saldo());

		/* ja kassassa oleva rahamäärä kasvaa ladatulla summalla */
	    assertEquals(100300, paate.kassassaRahaa());
	}
}
