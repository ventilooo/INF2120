/**
 * 
 */
package util;

import tda.Individu;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author lefebvre_b
 *
 */
public class Personne implements Individu {

	private String nom;
	private ArrayList<String> prenoms;
	private Date dateNaissance;
	private int reference;
	private Individu parent1;
	private Individu parent2;

	public Personne(String nom, ArrayList<String> prenoms, Date date, int ref) {
		this.nom = nom;
		this.prenoms = prenoms;
		this.dateNaissance = date;
		this.reference = ref;
		this.parent1 = null;
		this.parent2 = null;
	}

	public Personne(String nom, ArrayList<String> prenoms, Date date) {
		this(nom, prenoms, date, -1);
	}

	@Override
	public void definirParent1(Individu p) {
		this.parent1 = p;
	}

	@Override
	public void definirParent2(Individu p) {
		this.parent2 = p;
	}

	@Override
	public void definirLaReference(int ref) {
		this.reference = ref;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * On suppose que les noms sont uniques:
	 * 2 personnes sont égales si leurs noms sont égaux
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Personne))
			return false;
		Personne other = (Personne) obj;
		return nom == other.nom;
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

	@Override
	public Individu leParent1() {
		return parent1;
	}

	@Override
	public Individu leParent2() {
		return parent2;
	}

	public ArrayList<String> lesPrenoms() {
		return prenoms;
	}

	@Override
	public String toString() {
		return this.leNom();
	}

	@Override
	public int compareTo(Individu o) {
		return this.laDate().compareTo(o.laDate());
	}

}
