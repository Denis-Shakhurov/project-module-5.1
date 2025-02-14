package org.example.dao;

import jakarta.persistence.criteria.*;
import org.example.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDAO {

    private final SessionFactory sessionFactory;

    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> findAll(int offset, int limit) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);
        criteria.select(root);
        Query<Task> query = getSession().createQuery(criteria);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Task> root = criteria.from(Task.class);
        criteria.select(builder.count(root));
        Query<Long> query = getSession().createQuery(criteria);
        return query.uniqueResult().intValue();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<Task> findById(int id) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);
        criteria.select(root).where(builder.equal(root.get("id"), id));
        Query<Task> query = getSession().createQuery(criteria);
        return Optional.of(query.uniqueResult());
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void updateOrSave(Task task) {
        getSession().persist(task);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        getSession().delete(findById(id));
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
