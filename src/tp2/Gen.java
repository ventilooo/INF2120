package tp2;

import tda.Genealogie;
import tda.Individu;
import tda.Liste;
import util.ListeChaine;
import util.Personne;


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

    public Liste<Personne> lesParents(Individu moi) {
        Liste<Personne> tmp = new ListeChaine<>();
        return tmp;
    }

    public Liste<Individu> laFratrie(Individu moi) {
        Liste<Individu> tmp = new ListeChaine<>();
        return tmp;
    }

    public Liste<Individu> lesEnfants(Individu p1, Individu p2) {
        Liste<Individu> tmp = new ListeChaine<>();
        return tmp;
    }

    public Liste<Individu> lesPetitsEnfants(Individu p1, Individu p2) {
        Liste<Individu> tmp = new ListeChaine<>();
        return tmp;
    }

    public void ajout(Individu moi) {
        listeIndividu.insererFin(moi);
        moi.definirLaReference(listeIndividu.longueur());
    }

    public void definirParent1(Individu moi, Individu parent1) {
        moi.definirParent1(parent1.laReference());
    }

    public void definirParent2(Individu moi, Individu parent2) {
        moi.definirParent2(parent2.laReference());
    }

    public boolean testParent1(Individu moi, Individu parent1) {
        for (Individu element : listeIndividu) {
            if (element.leParent1() == parent1.laReference()) {
                return true;
            }
        }
        return false;
    }

    public boolean testParent2(Individu moi, Individu parent2) {
        for (Individu element : listeIndividu) {
            if (element.leParent2() == parent2.laReference()) {
                return true;
            }
        }
        return false;
    }

    public boolean testParentsConnus(Individu moi) {
        return moi.leParent1() > -1 && moi.leParent2() > -1;
    }

}
