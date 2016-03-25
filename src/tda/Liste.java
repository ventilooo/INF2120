/**
 *
 */
package tda;

import java.util.NoSuchElementException;

/**
 * @author lefebvre_b
 *
 */
public interface Liste<T> extends Iterable<T> {
    /**
     * Détermine si la liste est vide.
     * @return true si la liste est vide, false autrement
     */
    public boolean estVide();

    /**
     * Détermine le nombre d'éléments dans la liste.
     * @return le nombre d'éléments dans la liste
     */
    public int longueur();

    /**
     * Retourne le premier élément de la liste.
     * @return le premier élément de la liste
     * @throws NoSuchElementException si liste vide
     */
    public T elementDebut();

    /**
     * Retourne le dernier élément de la liste.
     * @return le dernier élément de la liste
     * @throws NoSuchElementException si liste vide
     */
    public T elementFin();

    /**
     * Retourne l'élément en position pos dans la liste.
     * Le premier élément est en position 0.
     * @return l'élément en position pos dans la liste
     * @throws NoSuchElementException si pos < 0 ou pos >= longueur()
     */
    public T elementPosition( int pos);

    /**
     * insère l'élément en début de liste.
     * @param element l'élément à insérer
     */
    public void insererDebut( T element);

    /**
     * Insère l'élément en fin de liste.
     * @param element l'élément à insérer
     */
    public void insererFin( T element);

    /**
     * Insère l'élément en position pos dans la liste.
     * Si pos = 0, insère au début; si pos = longueur, insère à la fin
     * @param element l'élément à insérer
     * @param pos position où insérer l'élément
     * @throws NoSuchElementException si pos < 0 ou pos > longueur()
     */
    public void insererPosition( T element, int pos);

    /**
     * Retire et retourne l'élément au début de la liste.
     * @return l'élément en début de liste
     * @throws NoSuchElementException si liste vide
     */
    public T retirerDebut();

    /**
     * Retire et retourne l'élément en fin de liste.
     * @return l'élément en fin de liste
     * @throws NoSuchElementException si liste vide
     */
    public T retirerFin();

    /**
     * Retire et retourne l'élément en position pos dans la liste.
     * @return l'objet en position pos dans la liste
     * @throws NoSuchElementException si pos < 0 ou pos >= longueur()
     */
    public T retirerPosition( int pos);

    /**
     * réalise le tri des éléments de la liste selon l'ordre défini par la méthode compareTo
     */
    public void tri();
}
