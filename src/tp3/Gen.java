package tp3;

import tda.Genealogie;
import tda.Individu;
import tda.Liste;
import util.ListeChaine;

public class Gen<U extends Individu> implements Genealogie<U> {

    private ListeChaine<U> arbre;

    public Gen() {
        arbre = new ListeChaine<U>();
    }

    @Override
    public int nombreIndividus() {
        return arbre.longueur();
    }

    @Override
    public Liste<U> lesIndividus() {
        Liste<U> inds = new ListeChaine<U>();
        for (U ind : arbre) {
            inds.insererFin(ind);
        }
        return inds;
    }

    public Individu lIndividu(int pos) {
        return arbre.elementPosition(pos);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Liste<U> lesParents(U moi) {
        Liste<U> ps = new ListeChaine<U>();
        ps.insererFin((U) moi.leParent1());
        ps.insererFin((U) moi.leParent2());
        return ps;
    }

    /* (non-Javadoc)
     * @see tda.Genealogie#fratrie(tda.Individu)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Liste<U> laFratrie(U moi) {
        U p1 = (U) moi.leParent1();
        U p2 = (U) moi.leParent2();
        Liste<U> fs = this.lesEnfants(p1, p2);
        // la liste est vide s'il n'y a pas de parents
        int ipos = -1;
        for (int i = 0; i < fs.longueur() && ipos < 0; i++) {
            if (fs.elementPosition(i).equals(moi)) {
                ipos = i;
            }
        }
        if (ipos >= 0) {
            fs.retirerPosition(ipos);
        }
        fs.tri();
        return fs;
    }

    @Override
    public Liste<U> lesEnfants(U p1, U p2) {
        Liste<U> fs = new ListeChaine<U>();
        for (U elem : arbre) {
            try {
                if (p1 != null && p2 != null) {
                    if (elem.leParent1().equals(p1) && elem.leParent2().equals(p2)
                            || elem.leParent1().equals(p2) && elem.leParent2().equals(p1)) {
                        fs.insererFin(elem);
                    }
                } else if (p1 != null) {
                    if (elem.leParent1().equals(p1) || elem.leParent2().equals(p1)) {
                        fs.insererFin(elem);
                    }
                } else if (p2 != null) {
                    if (elem.leParent1().equals(p2) || elem.leParent2().equals(p2)) {
                        fs.insererFin(elem);
                    }
                }
            } catch (NullPointerException ex) {
                // au moins un des parents de elem est null, dans ce cas p1 ou p2 doit être null aussi
                if (elem.leParent2() != null && (p1 == null || p2 == null)) {
                    if (elem.leParent2().equals(p2) || elem.leParent2().equals(p1)) {
                        fs.insererDebut(elem);
                    }
                } else if (elem.leParent1() != null && (p1 == null || p2 == null)) {
                    if (elem.leParent1().equals(p1) || elem.leParent1().equals(p2)) {
                        fs.insererDebut(elem);
                    }
                }
            }
        }
        fs.tri();
        return fs;
    }

    @Override
    public Liste<U> lesPetitsEnfants(U p1, U p2) {
        Liste<U> fs = new ListeChaine<U>();
        for (U elem : this.lesEnfants(p1, p2)) {
            for (U es : this.lesEnfants(elem, null)) {
                fs.insererFin(es);
            }
        }
        fs.tri();
        return fs;
    }

    @Override
    public void ajout(U moi) {
        int ind = arbre.longueur();
        moi.definirLaReference(ind);
        arbre.insererFin(moi);
    }

    @Override
    public void definirParent1(U moi, U parent1) {
        moi.definirParent1(parent1);
    }

    @Override
    public void definirParent2(U moi, U parent2) {
        moi.definirParent2(parent2);
    }

}
