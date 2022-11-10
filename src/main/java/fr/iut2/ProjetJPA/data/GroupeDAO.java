package fr.iut2.ProjetJPA.data;



import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GroupeDAO {

    public static Groupe retrieveById(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        Groupe grp = em.find(Groupe.class, id);
        // grp est maintenant un objet de la classe Groupe
        // ou NULL si le groupe n'existe pas

        // Close the entity manager
        em.close();
        return grp;

    }

    public static Groupe create(String nom) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // create
        em.getTransaction().begin();

        // create new groupe
        Groupe groupe = new Groupe();
        groupe.setNom(nom);

        em.persist(groupe);

        // On peut maintenant accéder au champ id de l'objet créé
        // (champ autoincrémenté)
        int id = groupe.getId();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return groupe;
    }

    public static void remove(Groupe groupe) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // Le groupe passé en paramètre doit être associé à l'EM
        if (!em.contains(groupe)) {
            groupe = em.merge(groupe);
        }

        // Supprime l'entité courante mais aussi les entités (étudiants) liées
        // grâce à l'annotation cascade = {CascadeType.REMOVE} dans la classe Groupe
        em.remove(groupe);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();
    }

    public static int removeAll() {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // RemoveAll
        int deletedCount = em.createQuery("DELETE FROM Groupe").executeUpdate();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return deletedCount;
    }

    // supprimer un module dans un groupe
    public static int updateListModuleGroupe(int idGroupe, Module module) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // RemoveAll
        int deletedCount= em.createQuery("DELETE FROM Groupe g WHERE g.id = :idgroupe and g.modules = :module ")
                            .setParameter("idgroupe", idGroupe)
                            .setParameter("module",module)
                            .executeUpdate();
        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return deletedCount;
    }
    public static List<Groupe> getAll() {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        // Recherche 
        Query q = em.createQuery("SELECT g FROM Groupe g");

        @SuppressWarnings("unchecked")
        List<Groupe> listGroupe = q.getResultList();

        return listGroupe;
    }
    public static Groupe update(Groupe groupe) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // Attacher une entité persistante (etudiant) à l’EntityManager courant  pour réaliser la modification
        em.merge(groupe);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return groupe;
    }

}
