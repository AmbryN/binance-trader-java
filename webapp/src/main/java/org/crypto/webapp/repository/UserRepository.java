package org.crypto.webapp.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import org.crypto.webapp.beans.User;

import java.util.List;

@Dependent
public class UserRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public List<User> findAll() {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.select(criteriaQuery.from(User.class));
        return em.createQuery(criteriaQuery).getResultList();
    }
}
