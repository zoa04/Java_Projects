package com.university.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public abstract class GenericRepository<T> {
    @PersistenceContext
    protected EntityManager em;
    private final Class<T> entityClass;
    protected GenericRepository(Class<T> entityClass) { this.entityClass = entityClass; }

    @Transactional
    public T save(T entity) { em.persist(entity); return entity; }

    public T find(Long id) { return em.find(entityClass, id); }

    public List<T> listAll() {
        return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
    }
}
