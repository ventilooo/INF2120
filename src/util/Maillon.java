/**
 *
 */
package util;

/**
 * @author lefebvre_b
 *
 */
public class Maillon<T> {
	private T valeur;
	private Maillon<T> suivant;
	/**
	 * @param valeur, la valeur du maillon
	 * @param suivant, le maillon suivant
	 */
	public Maillon(T valeur, Maillon<T> suivant) {
		super();
		this.valeur = valeur;
		this.suivant = suivant;
	}
	/**
	 * @param valeur, la valeur du maillon
	 */
	public Maillon(T valeur) {
		this(valeur, null);
	}

	/**
	 * @param valeur, la valeur du maillon
	 */
	public void definirValeur(T valeur) {
		this.valeur = valeur;
	}
	/**
	 * @return, retourne la valeur du maillon
	 */
	public T laValeur() {
		return valeur;
	}
	/**
	 * @return, retourne le maillon suivant
	 */
	public Maillon<T> leSuivant() {
		return suivant;
	}
	/**
	 * @param suivant, définit le maillon suivant
	 */
	public void definirSuivant(Maillon<T> suivant) {
		this.suivant = suivant;
	}
}
