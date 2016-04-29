/**
 *
 */
package tda;

import java.util.Date;

/**
 * @author lefebvre_b
 *
 */
public interface Individu extends Comparable<Individu> {

	/**
	 * @return, retourne la date de naissance de l'individu
	 */
	Date laDate();

	/**
	 * @return, retourne un entier référençant de manière unique l'individu,
	 * peut être -1 si la référence n'est pas connue.
	 */
	int laReference();

	/**
	 * @param ref, un entier permettant de définir de manière unique la référence de l'individu
	 */
	void definirLaReference(int ref);

	/**
	 * @return, retourne le premier parent de l'individu
	 * -1 si le premier parent n'est pas connu
	 */
	Individu leParent1();

	/**
	 * @return, retourne le second parent de l'individu
	 * -1 si le second parent n'est pas connu
	 */
	Individu leParent2();

	/**
	 * @param p, le premier parent
	 */
	void definirParent1(Individu p);

	/**
	 * @param p, le second parent
	 */
	void definirParent2(Individu p);


}
