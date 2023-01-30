package org.crypto.webapp.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import org.crypto.webapp.beans.Utilisateur;

import java.util.List;

@Dependent
public class UserRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<Utilisateur> findAll() {
        CriteriaQuery<Utilisateur> criteriaQuery = em.getCriteriaBuilder().createQuery(Utilisateur.class);
        criteriaQuery.select(criteriaQuery.from(Utilisateur.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
}
