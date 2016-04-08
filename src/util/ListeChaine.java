/**
 * 
 */
package util;

import tda.Liste;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author lefebvre_b
 *
 */
public class ListeChaine<T extends Comparable<? super T>> implements Liste<T>, Iterable<T> {

	protected Maillon<T> tete;

	/* (non-Javadoc)
	 * @see tda.Liste#estVide()
	 */
	@Override
	public boolean estVide() {
		return tete == null;
	}

	/* (non-Javadoc)
	 * @see tda.Liste#longueur()
	 */
	@Override
	public int longueur() {
		int nbElements = 0;
		Maillon<T> elem = tete;
		while (elem != null) {
			nbElements++;
			elem = elem.leSuivant();
		}
		return nbElements;
	}

	/* (non-Javadoc)
	 * @see tda.Liste#elementDebut()
	 */
	@Override
	public T elementDebut() {
		return this.elementPosition(0);
	}

	/* (non-Javadoc)
	 * @see tda.Liste#elementFin()
	 */
	@Override
	public T elementFin() {
		return this.elementPosition(this.longueur() - 1);
	}

	/**
	 * @param pos, un entier désignant une position dans la liste.
	 * @return, le maillon de la liste à la position désignée.
	 * Si pos < 0, le premier élément de la liste est retourné.
	 * Si pos >= à la longueur de la liste, une exception nulle se produit
	 */
	protected Maillon<T> maillonPosition(int pos) {
		Maillon<T> elem = tete;
		for (int i = 0; i < pos; i++) {
			elem = elem.leSuivant();
		}
		return elem;
	}

	/* (non-Javadoc)
	 * @see tda.Liste#elementPosition(int)
	 */
	@Override
	public T elementPosition(int pos) {
		if (pos < 0 || pos >= this.longueur()) {
			throw new NoSuchElementException();
		}
		return this.maillonPosition(pos).laValeur();
	}

	/* (non-Javadoc)
	 * @see tda.Liste#insererDebut(java.lang.Object)
	 */
	@Override
	public void insererDebut(T element) {
		this.insererPosition(element, 0);
	}

	/* (non-Javadoc)
	 * @see tda.Liste#insererFin(java.lang.Object)
	 */
	@Override
	public void insererFin(T element) {
		this.insererPosition(element, this.longueur());
	}

	/* (non-Javadoc)
	 * @see tda.Liste#insererPosition(java.lang.Object, int)
	 */
	@Override
	public void insererPosition(T element, int pos) {
		if (pos < 0 || pos > this.longueur()) throw new NoSuchElementException();
		Maillon<T> nouveau = new Maillon<T>(element);
		if (pos == 0) {
			nouveau.definirSuivant(tete);
			tete = nouveau;
		} else {
			Maillon<T> elem = this.maillonPosition(pos - 1);
			nouveau.definirSuivant(elem.leSuivant());
			elem.definirSuivant(nouveau);
		}
	}

	/* (non-Javadoc)
	 * @see tda.Liste#retirerDebut()
	 */
	@Override
	public T retirerDebut() {
		return this.retirerPosition(0);
	}

	/* (non-Javadoc)
	 * @see tda.Liste#retirerFin()
	 */
	@Override
	public T retirerFin() {
		return this.retirerPosition(this.longueur() - 1);
	}

	/* (non-Javadoc)
	 * @see tda.Liste#retirerPosition(int)
	 */
	@Override
	public T retirerPosition(int pos) {
		if (pos < 0 || pos >= this.longueur()) throw new NoSuchElementException();
		if (pos == 0) {
			T val = tete.laValeur();
			tete = tete.leSuivant();
			return val;
		}
		Maillon<T> elem = this.maillonPosition(pos - 1);
		Maillon<T> sup = elem.leSuivant();
		elem.definirSuivant(sup.leSuivant());
		return sup.laValeur();
	}

	@Override
	public Iterator<T> iterator() {
		return new IterateurListe<T>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ListeChaine)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		ListeChaine<T> lp = (ListeChaine<T>) obj;
		int i;
		boolean eq = true;
		for (i = 0; i < this.longueur() && i < lp.longueur() && eq; i++) {
			T elem = this.elementPosition(i);
			if (elem != null) {
				eq = this.elementPosition(i).equals(lp.elementPosition(i));
			} else {
				eq = lp.elementPosition(i) == null;
			}
		}
		return eq && i >= this.longueur() && i >= lp.longueur();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String ch = "ListeChaine[";
		if (tete != null) {
			if (tete.laValeur() != null) {
				ch += tete.laValeur().toString();
			} else {
				ch += "null";
			}

			Maillon<T> courant = tete.leSuivant();
			while (courant != null) {
				if (courant.laValeur() != null) {
					ch += ", " + courant.laValeur().toString();
				} else {
					ch += ", null";
				}
				courant = courant.leSuivant();
			}
		}
		ch += "]";
		return ch;
	}

	public void tri() {
		ArrayList<T> ls = new ArrayList<T>();
		for (T elem : this) {
			ls.add(elem);
		}
		Collections.sort(ls);
		tete = null;
		for (T elem : ls) {
			this.insererFin(elem);
		}
	}

	protected class IterateurListe<E> implements Iterator<E> {

		protected Maillon<E> courant;

		@SuppressWarnings("unchecked")
		IterateurListe() {
			courant = (Maillon<E>) tete;
		}

		@Override
		public boolean hasNext() {
			return courant != null;
		}

		@Override
		public E next() {
			Maillon<E> prec = courant;
			courant = courant.leSuivant();
			return prec.laValeur();
		}
		/*
		 * Plus nécessaire depuis Jre 1.8
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		*/
	}
}
