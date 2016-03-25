/**
 *
 */
package tda;

/**
 * @author Lefebvre Bernard
 *
 */
public interface Genealogie<U extends Individu> {

    /**
     * @return , retourne le nombre d'individus de la généalogie
     */
    public int nombreIndividus();

    /**
     * @return , retourne la liste chaînée formée des individus de la généalogie
     */
    public Liste<U> lesIndividus();

    /**
     * @param pos, un entier qui est la référence d'un individu dans la généalogie
     * @return , retourne l'individu correspondant
     */
    public U lIndividu(int pos);

    /**
     * @param moi, un individu
     * @return , retourne la liste chaînée des deux individus parents,
     * cette liste peut comporter
     * des éléments nuls si les parents correspondants ne sont pas connus.
     */
    public Liste<U> lesParents(U moi);

    /**
     * @param moi, un individu
     * @return , retourne la liste chaînée triée en age décroissant
     * des individus qui ont les mêmes parents que moi.
     */
    public Liste<U> laFratrie(U moi);

    /**
     * @param p1, le premier parent
     * @param p2, le second parent
     * @return retourne la liste chaînée triée en age décroissant
     * des enfants de ces parents, si l'un des parents est nul
     * on retourne la liste des enfants de l'autre parent.
     */
    public Liste<U> lesEnfants(U p1, U p2);

    /**
     * @param p1, le premier parent
     * @param p2, le second parent
     * @return retourne la liste chaînée triée en age décroissant
     * des petits enfants de ces parents, si l'un des parents est nul
     * on retourne la liste des petits enfants de l'autre parent.
     * Si les 2 sont nuls, retourne une liste vide.
     */
    public Liste<U> lesPetitsEnfants(U p1, U p2);

    /**
     * @param moi, un individu qui est ajouté à la généalogie et
     * dont les parents sont inconnus (nuls).
     */
    public void ajout(U moi);

    /**
     * @param moi, un individu
     * @param parent1, un individu premier parent de moi
     * peut être nul si moi n'a pas de premier parent.
     */
    public void definirParent1(U moi, U parent1);

    /**
     * @param moi, un individu
     * @param parent2, un individu second parent de moi
     * peut être nul si moi n'a pas de second parent.
     */
    public void definirParent2(U moi, U parent2);

}
