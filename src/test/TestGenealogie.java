package test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import tda.Individu;
import tp2.Gen;
import util.ListeChaine;
import util.Personne;

public class TestGenealogie {
    private Personne p1;
    private Personne p2;
    private Personne p3;
    private Personne p4;
    private Personne p5;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> prenoms = new ArrayList<String>();
        DateFormat dfm = new SimpleDateFormat("Y");
        dfm.setTimeZone(TimeZone.getTimeZone("Canada/Montreal"));
        try {
            p1 = new Personne("e1",null, dfm.parse("1990"));
            p2 = new Personne("e2",null, dfm.parse("1994"));
            p3 = new Personne("p1",null, dfm.parse("1970"));
            p4 = new Personne("p2",null, dfm.parse("1972"));
            prenoms.add("pr1");
            prenoms.add("pr2");
            p5 = new Personne("gp1",prenoms, dfm.parse("1945"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAjout() {
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.ajout(p5);
        assertSame(5, g.nombreIndividus());
    }

    @Test
    public void testDefinirParent1() {
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.definirParent1(p1, p3);
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        e.insererFin(p1);
        assertEquals(e, g.lesEnfants(p3, null));
        assertEquals(e, g.lesEnfants(null, p3));
    }

    @Test
    public void testDefinirParent2() {
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        e.insererFin(p1);
        assertEquals(e, g.lesEnfants(p3, p4));
        assertEquals(g.lesEnfants(p4, p3), g.lesEnfants(p3, p4));
        assertEquals(e, g.lesEnfants(p3, null));
        assertEquals(e, g.lesEnfants(null, p3));
    }

    @Test
    public void testLesParents() {
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        e.insererFin(null);
        e.insererFin(null);
        assertEquals(e, g.lesParents(p1));
        e = new ListeChaine<Individu>();
        e.insererFin(p3);
        e.insererFin(null);
        g.definirParent1(p1, p3);
        assertEquals(e,g.lesParents(p1));
        e = new ListeChaine<Individu>();
        e.insererFin(null);
        e.insererFin(p4);
        g.definirParent2(p2,p4);
        assertEquals(e,g.lesParents(p2));
        e = new ListeChaine<Individu>();
        g.definirParent2(p1, p4);
        e = new ListeChaine<Individu>();
        e.insererFin(p3);
        e.insererFin(p4);
        assertEquals(e, g.lesParents(p1));
    }

    @Test
    public void testLaFratrie() {
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        assertEquals(e,g.laFratrie(p1));
        g.definirParent1(p2, p3);
        g.definirParent2(p2, p4);
        e.insererFin(p1);
        assertEquals(e,g.laFratrie(p2));
        e = new ListeChaine<Individu>();
        g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        g.definirParent1(p2, p3);
        g.definirParent2(p2, null);
        assertEquals(e,g.laFratrie(p1));
        e = new ListeChaine<Individu>();
        g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.definirParent2(p1, p4);
        g.definirParent1(p2, p4);
        g.definirParent1(p1, null);
        g.definirParent2(p2, null);
        e.insererFin(p1);
        assertEquals(e,g.laFratrie(p2));
    }

    @Test
    public void testLesEnfants() {
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        g.definirParent1(p2, p3);
        g.definirParent2(p2, p4);
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        e.insererFin(p1);
        e.insererFin(p2);
        assertEquals(e,g.lesEnfants(p3, p4));
        g = new Gen<Personne>();
        g.ajout(p4);
        g.ajout(p2);
        g.ajout(p1);
        g.definirParent1(p2, p4);
        g.definirParent1(p1, p4);
        g.definirParent2(p2, null);
        g.definirParent2(p1, null);
        e = new ListeChaine<Individu>();
        e.insererFin(p1);
        e.insererFin(p2);
        assertEquals(e,g.lesEnfants(null, p4));
        g.ajout(p3);
        g.ajout(p5);
        g.definirParent2(p3, p5);
        e = new ListeChaine<Individu>();
        e.insererFin(p3);
        assertEquals(e,g.lesEnfants(p5, null));
    }

    @Test
    public void testNombreIndividus() {
        Gen<Personne> g = new Gen<Personne>();
        assertSame(0, g.nombreIndividus());
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.ajout(p5);
        assertSame(5, g.nombreIndividus());
    }

}
