/**
 *
 */
package util;

import java.util.ArrayList;
import java.util.Date;

import tda.Individu;

/**
 * @author lefebvre_b
 *
 */
public class Personne implements Individu, Comparable<Individu> {

    private String nom;
    private ArrayList<String> prenoms;
    private Date dateNaissance;
    private int reference;
    private int parent1;
    private int parent2;

    public Personne(String nom, ArrayList<String> prenoms, Date date, int ref) {
        this.nom = nom;
        this.prenoms = prenoms;
        this.dateNaissance = date;
        this.reference = ref;
        this.parent1 = -1;
        this.parent2 = -1;
    }

    public Personne(String nom, ArrayList<String> prenoms, Date date) {
        this(nom, prenoms, date, -1);
    }

    @Override
    public void definirParent1(int ref) {
        this.parent1 = ref;
    }

    @Override
    public void definirParent2(int ref) {
        this.parent2 = ref;
    }

    @Override
    public void definirLaReference(int ref) {
        this.reference = ref;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Personne))
            return false;
        Personne other = (Personne) obj;
        if (reference != other.reference)
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see tda.Individu#lAge()
     */
    @Override
    public Date laDate() {
        return dateNaissance;
    }

    /* (non-Javadoc)
     * @see tda.Individu#leReferent()
     */
    @Override
    public int laReference() {
        return reference;
    }

    public String leNom() {
        return nom;
    }

    public int leParent1() {
        return parent1;
    }

    public int leParent2() {
        return parent2;
    }

    public ArrayList<String> lesPrenoms() {
        return prenoms;
    }

    @Override
    public String toString() {
        return "Personne(" + reference + ", " + nom + ", " + prenoms + ", " + dateNaissance + ")";
    }

    @Override
    public int compareTo(Individu p) {
        return this.laDate().compareTo(p.laDate());
    }
}