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
        if (moi.leParent1() > -1) {
            tmp.insererFin(lIndividu(moi.leParent1()));
        } else {
            tmp.insererFin(null);
        }
        if (moi.leParent2() > -1) {
            tmp.insererFin(lIndividu(moi.leParent2()));
        } else {
            tmp.insererFin(null);
        }
        return tmp;
    }

    public Liste<Individu> laFratrie(Individu moi) {
        Liste<Individu> tmp = new ListeChaine<>();
        for (Individu element : listeIndividu) {
            if (element.leParent1() == moi.leParent1() && element.leParent2() == moi.leParent2() && !element.equals(moi)) {
                tmp.insererFin(element);
            } else if (element.leParent1() == moi.leParent2() && element.leParent2() == moi.leParent1() && !element.equals(moi)) {
                tmp.insererFin(element);
            }
        }
        tmp.tri();
        return tmp;
    }

    public Liste<Individu> lesEnfants(Individu p1, Individu p2) {
        Liste<Individu> tmp = new ListeChaine<>();
        ajouterEnfants(p1, tmp);
        ajouterEnfants(p2, tmp);
        tmp.tri();
        return tmp;
    }

    public Liste<Individu> lesPetitsEnfants(Individu p1, Individu p2) {
        Liste<Individu> tmp = new ListeChaine<>();
        for (Individu element : lesEnfants(p1, p2)) {
            if (!lesEnfants(element, null).estVide()) {
                for (Individu individu : lesEnfants(element, null)) {
                    tmp.insererFin(individu);
                }
            }
        }
        tmp.tri();
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

    public void ajouterEnfants(Individu parent, Liste<Individu> liste) {
        if (parent != null) {
            for (Individu element : listeIndividu) {
                if (element.leParent1() == parent.laReference()) {
                    liste.insererFin(element);
                } else if (element.leParent2() == parent.laReference()) {
                    liste.insererFin(element);
                }
            }
        }
    }

}
