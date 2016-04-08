package test;

import org.junit.Before;
import org.junit.Test;
import tda.Individu;
import tp3.Gen;
import util.ListeChaine;
import util.Personne;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class TestGenealogie {

	private static Gen<Personne> gen;
	private Gen<Personne> g;
	private Personne p1;
	private Personne p2;
	private Personne p3;
	private Personne p4;
	private Personne p5;
	private Personne p6;
	private Personne p7;
	private Personne p8;
	private Personne p9;
	private Personne p10;
	private Personne p11;
	private Personne p12;
	private Personne p13;
	private Personne p14;
	private Personne p15;


	public static void definirGenealogie(Gen<Personne> g) {
		gen = g;
	}

	private static Personne laPersonne(Personne moi) {
		Personne p = null;
		for (Personne ind : gen.lesIndividus()) {
			if (ind.equals(moi)) {
				p = ind;
			}
		}
		return p;
	}


	@Before
	public void setUp() throws Exception {
		g = gen;
		DateFormat dfm = new SimpleDateFormat("Y");
		dfm.setTimeZone(TimeZone.getTimeZone("Canada/Montreal"));
		try {
			p1 = new Personne("Tremblay", new ArrayList<String>(Arrays.asList("Guy")), dfm.parse("1990"));
			p2 = new Personne("Mili", new ArrayList<String>(Arrays.asList("Hafedh")), dfm.parse("1994"));
			p3 = new Personne("Gagnon", new ArrayList<String>(Arrays.asList("Étienne")), dfm.parse("1991"));
			p4 = new Personne("Laforest", new ArrayList<String>(Arrays.asList("Louise")), dfm.parse("1992"));
			p5 = new Personne("Cherkaoui", new ArrayList<String>(Arrays.asList("Omar")), dfm.parse("1967"));
			p6 = new Personne("Kerhervé", new ArrayList<String>(Arrays.asList("Brigitte")), dfm.parse("1965"));
			p7 = new Personne("Moha", new ArrayList<String>(Arrays.asList("Naouel")), dfm.parse("1964"));
			p8 = new Personne("Boukadoum", new ArrayList<String>(Arrays.asList("Mounir")), dfm.parse("1963"));
			p9 = new Personne("Villemaire", new ArrayList<String>(Arrays.asList("Roger")), dfm.parse("1945"));
			p10 = new Personne("Dupuis", new ArrayList<String>(Arrays.asList("Robert")), dfm.parse("1940"));
			p11 = new Personne("Gabrini", new ArrayList<String>(Arrays.asList("Philippe")), dfm.parse("1941"));
			p12 = new Personne("Bergeron", new ArrayList<String>(Arrays.asList("Anne")), dfm.parse("1923"));
			p13 = new Personne("Davidson", new ArrayList<String>(Arrays.asList("Paul")), dfm.parse("1948"));
			p14 = new Personne("Makarenkov", new ArrayList<String>(Arrays.asList("Vladimir")), dfm.parse("1962"));
			p15 = new Personne("Bouisset", new ArrayList<String>(Arrays.asList("Marc")), dfm.parse("1922"));
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testLesParents1() {
		// p15: Bouisset n'a pas de parents
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(null);
		e.insererFin(null);
		assertEquals(e, g.lesParents(laPersonne(p15)));
	}

	@Test
	public void testLesParents2() {
		// le seul second parent de p14: Makarenkov est p11: Gabrini
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(null);
		e.insererFin(p11);
		assertEquals(e, g.lesParents(laPersonne(p14)));
	}

	@Test
	public void testLesParents3() {
		// le seul premier parent de p13: Davidson est p12: Bergeron
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p12);
		e.insererFin(null);
		assertEquals(e, g.lesParents(laPersonne(p13)));
	}

	@Test
	public void testLesParents4() {
		// Les parents de p4: Laforest sont p7: Moha et p8: Boukadoum
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p7);
		e.insererFin(p8);
		assertEquals(e, g.lesParents(laPersonne(p4)));
	}

	@Test
	public void testLaFratrie1() {
		// La fraterie de P1: Tremblay est vide
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		assertEquals(e, g.laFratrie(laPersonne(p1)));
	}

	@Test
	public void testLaFratrie2() {
		// La fraterie de P4: Laforest est p3: Gagnon et p2: Mili
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p3);
		e.insererFin(p2);
		assertEquals(e, g.laFratrie(laPersonne(p4)));
	}

	@Test
	public void testLaFratrie3() {
		// La fraterie de p10: Dupuis est p11: Gabrini
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p11);
		assertEquals(e, g.laFratrie(laPersonne(p10)));
	}

	@Test
	public void testLesEnfants1() {
		// Les enfants de p9: Villemaire et p10: Dupuis sont p7: Moha et  p6: Kerhervé
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p7);
		e.insererFin(p6);
		assertEquals(e, g.lesEnfants(laPersonne(p9), laPersonne(p10)));
	}

	@Test
	public void testLesEnfants2() {
		// Les enfants de p12: Bergeron sont p10: Dupuis, p11: Gabrini et p13: Davidson
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p10);
		e.insererFin(p11);
		e.insererFin(p13);
		assertEquals(e, g.lesEnfants(null, laPersonne(p12)));
	}

	@Test
	public void testLesEnfants3() {
		// Les enfants de p15: Bouisset et p12: Bergeron sont p10: Duouis et p11: Gabrini
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p10);
		e.insererFin(p11);
		assertEquals(e, g.lesEnfants(laPersonne(p15), laPersonne(p12)));
	}

	@Test
	public void testLesPetitsEnfants1() {
		//Les petits enfants de p9: Villemaire et p10: Duouis sont: p1: Tremblay, p3: Gagnon, p4: Laforest et p2: Mili
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p1);
		e.insererFin(p3);
		e.insererFin(p4);
		e.insererFin(p2);
		assertEquals(e, g.lesPetitsEnfants(laPersonne(p9), laPersonne(p10)));
	}

	@Test
	public void testLesPetitsEnfants2() {
		//Les petits enfants de p12: Bergeron sont p14: Makarenkov, p7: Moha et P6: Kerhervé
		ListeChaine<Individu> e = new ListeChaine<Individu>();
		e.insererFin(p14);
		e.insererFin(p7);
		e.insererFin(p6);
		assertEquals(e, g.lesPetitsEnfants(null, laPersonne(p12)));
		assertEquals(e, g.lesPetitsEnfants(laPersonne(p12), null));
	}

	@Test
	public void testNombreIndividus() {
		assertSame(15, g.nombreIndividus());
	}

}
