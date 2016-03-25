package tp2;

import tda.Genealogie;
import tda.Individu;
import tda.Liste;
import util.ListeChaine;


/**
 * Created by SZAI29079604 on 07/03/2016.
 */
public class Gen<U extends Individu> implements Genealogie {

    ListeChaine<Individu> listeIndividu = new ListeChaine<>();

    public int nombreIndividus() {
        return listeIndividu.longueur();
    }

    public Liste<Individu> lesIndividus() {
        return listeIndividu;
    }

    public Individu lIndividu(int pos) {
        return listeIndividu.elementPosition(pos);
    }

    public Liste<Individu> lesParents(Individu moi) {
        Liste<Individu> tmp = new ListeChaine<>();
        for (Individu element : listeIndividu) {
            if (element.laReference() == moi.leParent1()) {
                tmp.insererFin(element);
            }
        }
        if (tmp.estVide()) {
            tmp.insererFin(null);
        }
        for (Individu element : listeIndividu) {
            if (element.laReference() == moi.leParent2()) {
                tmp.insererFin(element);
            }
        }
        if (tmp.longueur() == 1) {
            tmp.insererFin(null);
        }
        return tmp;
    }

    public Liste<Individu> laFratrie(Individu moi) {
        Liste<Individu> tmp = new ListeChaine<>();
        if (testParentsConnus(moi)) {
        }
        return tmp;
    }

    public Liste<Individu> lesEnfants(Individu p1, Individu p2) {
        Liste<Individu> tmp = new ListeChaine<>();
        if (p1 == null) {
            for (Individu element : listeIndividu) {
                if (element.leParent2() == p2.laReference()) {
                    tmp.insererFin(element);
                } else if (element.leParent1() == p2.laReference()) {
                    tmp.insererFin(element);
                }
            }
        } else if (p2 == null) {
            for (Individu element : listeIndividu) {
                if (element.leParent1() == p1.laReference()) {
                    tmp.insererFin(element);
                } else if (element.leParent2() == p1.laReference()) {
                    tmp.insererFin(element);
                }
            }
        } else {
            for (Individu element : listeIndividu) {
                if (element.leParent1() == p1.laReference() && element.leParent2()
                        == p2.laReference()) {
                    tmp.insererFin(element);
                } else if (element.leParent1() == p2.laReference() && element.leParent2()
                        == p1.laReference()) {
                    tmp.insererFin(element);
                }
            }
        }
        tmp.tri();
        return tmp;
    }

    public Liste<Individu> lesPetitsEnfants(Individu p1, Individu p2) {
        Liste<Individu> tmp = new ListeChaine<>();
        return tmp;
    }

    public void ajout(Individu moi) {
        moi.definirLaReference(listeIndividu.longueur());
        listeIndividu.insererFin(moi);

    }

    public void definirParent1(Individu moi, Individu parent1) {
        if (parent1 == null) {
            moi.definirParent1(-1);
        } else {
            moi.definirParent1(parent1.laReference());
        }
    }

    public void definirParent2(Individu moi, Individu parent2) {
        if (parent2 == null) {
            moi.definirParent2(-1);
        } else {
            moi.definirParent2(parent2.laReference());
        }
    }

    public boolean testParentsConnus(Individu moi) {
        return moi.leParent1() > -1 && moi.leParent2() > -1;
    }

}
