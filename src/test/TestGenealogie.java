package test;

import org.junit.Before;
import org.junit.Test;
import tda.Individu;
import tp2.Gen;
import util.ListeChaine;
import util.Personne;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class TestGenealogie {
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

    @Before
    public void setUp() throws Exception {
        ArrayList<String> prenoms = new ArrayList<String>();
        DateFormat dfm = new SimpleDateFormat("Y");
        dfm.setTimeZone(TimeZone.getTimeZone("Canada/Montreal"));
        try {
            p1 = new Personne("e1",null, dfm.parse("1990"));
            p2 = new Personne("e2",null, dfm.parse("1994"));
            p7 = new Personne("e3", null, dfm.parse("1991"));
            p8 = new Personne("e4", null, dfm.parse("1993"));
            p3 = new Personne("p1",null, dfm.parse("1970"));
            p4 = new Personne("p2",null, dfm.parse("1972"));
            p9 = new Personne("p3", null, dfm.parse("1971"));
            p10 = new Personne("p4", null, dfm.parse("1969"));
            prenoms.add("pr1");
            prenoms.add("pr2");
            p5 = new Personne("gp1",prenoms, dfm.parse("1945"));
            p6 = new Personne("gp2", null, dfm.parse("1950"));
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
    public void testLesParents1() {
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        e.insererFin(null);
        e.insererFin(null);
        assertEquals(e, g.lesParents(p1));
    }

    @Test
    public void testLesParents2() {
        Gen<Personne> g = new Gen<Personne>();
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        e.insererFin(p3);
        e.insererFin(null);
        g.definirParent1(p1, p3);
        assertEquals(e,g.lesParents(p1));
    }

    @Test
    public void testLesParents3() {
        Gen<Personne> g = new Gen<Personne>();
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p4);
        e.insererFin(null);
        e.insererFin(p4);
        g.definirParent2(p2,p4);
        assertEquals(e,g.lesParents(p2));
    }

    @Test
    public void testLesParents4() {
        Gen<Personne> g = new Gen<Personne>();
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        e.insererFin(p3);
        e.insererFin(p4);
        assertEquals(e, g.lesParents(p1));
    }

    @Test
    public void testLaFratrie1() {
        Gen<Personne> g = new Gen<Personne>();
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        assertEquals(e,g.laFratrie(p1));
        g.definirParent1(p2, p3);
        g.definirParent2(p2, p4);
        e.insererFin(p1);
        assertEquals(e,g.laFratrie(p2));
    }

    @Test
    public void testLaFratrie2() {
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        g.definirParent1(p2, p3);
        g.definirParent2(p2, null);
        assertEquals(e,g.laFratrie(p1));
    }

    @Test
    public void testLaFratrie3() {
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        Gen<Personne> g = new Gen<Personne>();
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
    public void testLesEnfants1() {
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
    }

    @Test
    public void testLesEnfants2() {
        Gen<Personne> g = new Gen<Personne>();
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        g.ajout(p4);
        g.ajout(p2);
        g.ajout(p1);
        g.definirParent1(p2, p4);
        g.definirParent1(p1, p4);
        g.definirParent2(p2, null);
        g.definirParent2(p1, null);
        e.insererFin(p1);
        e.insererFin(p2);
        assertEquals(e,g.lesEnfants(null, p4));
    }

    @Test
    public void testLesEnfants3() {
        Gen<Personne> g = new Gen<Personne>();
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        g.ajout(p3);
        g.ajout(p5);
        g.definirParent2(p3, p5);
        e.insererFin(p3);
        assertEquals(e,g.lesEnfants(p5, null));
    }

    @Test
    public void testLesPetitsEnfants1() {
        Gen<Personne> g = new Gen<Personne>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.ajout(p5);
        g.ajout(p6);
        g.ajout(p7);
        g.ajout(p8);
        g.ajout(p9);
        g.ajout(p10);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        g.definirParent1(p2, p3);
        g.definirParent2(p2, p4);
        g.definirParent2(p8, p9);
        g.definirParent1(p8, p10);
        g.definirParent2(p7, p9);
        g.definirParent1(p7, p10);
        g.definirParent1(p4, p5);
        g.definirParent2(p4, p6);
        g.definirParent1(p9, p5);
        g.definirParent2(p9, p6);
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        e.insererFin(p1);
        e.insererFin(p7);
        e.insererFin(p8);
        e.insererFin(p2);
        assertEquals(e, g.lesPetitsEnfants(p5, p6));
    }

    @Test
    public void testLesPetitsEnfants2() {
        Gen<Personne> g = new Gen<Personne>();
        ListeChaine<Individu> e = new ListeChaine<Individu>();
        g.ajout(p1);
        g.ajout(p2);
        g.ajout(p3);
        g.ajout(p4);
        g.ajout(p5);
        g.definirParent1(p1, p3);
        g.definirParent2(p1, p4);
        g.definirParent1(p2, p3);
        g.definirParent2(p2, p4);
        g.definirParent1(p3, p5);
        g.definirParent2(p3, null);
        e.insererFin(p1);
        e.insererFin(p2);
        assertEquals(e, g.lesPetitsEnfants(null, p5));
        assertEquals(e, g.lesPetitsEnfants(p5, null));
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
