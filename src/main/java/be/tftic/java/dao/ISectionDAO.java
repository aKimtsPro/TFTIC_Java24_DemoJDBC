package be.tftic.java.dao;

import be.tftic.java.models.Section;

import java.util.List;
import java.util.Optional;

public interface ISectionDAO {

    /**
     * Récupère toutes les section de la table "section" de la la db
     *
     * @return la liste des sections
     */
    List<Section> getAll();

    /**
     * Récupère un optional contenant la section ayant pour id, l'id en param.
     * Si une section n'existe pas, optional vide
     *
     * @param id l'id de la section à chercher
     * @return un optional contenant une section si trouvé, vide sinon.
     */
    Optional<Section> getOne(long id);

    /**
     * Insère un nouveau section en DB
     * @param toInsert les infos de la section à insérer.
     */
    void insert(Section toInsert);

}
