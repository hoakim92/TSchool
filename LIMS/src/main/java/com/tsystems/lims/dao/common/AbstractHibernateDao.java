package com.tsystems.lims.dao.common;

import com.tsystems.lims.config.WebAppInitializer;
import com.tsystems.lims.controller.PatientController;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Log4j
@ComponentScan(basePackages = "com.tsystems.lims.config")
@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<T> {

    protected Class<T> clazz;

    @PersistenceContext
    EntityManager entityManager;

    public final void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findOne(Integer id) {
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        return findAll(null, null, null);
    }

    public void create(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        log.info("CREATED ENTITY " + entity.toString());
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            log.info("USER " + ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    public void create(Iterable<T> entities) {
        entities.forEach(this::create);
        entityManager.flush();
    }

    public void update(T entity) {
        entityManager.merge(entity);
        log.info("UPDATED " + entity.toString());
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            log.info("USER " + ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    public void update(List<T> entities) {
        entities.forEach(this::update);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
        log.info("DELETED " + entity.toString());
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            log.info("USER " + ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    public void deleteById(Integer entityId) {
        delete(findOne(entityId));
    }

    public List<T> findAll(Integer offset, Integer count) {
        return findAll(null, offset, count);
    }

    public List<T> findAll(Map<String, String> parameters, Integer offset, Integer count) {
        Query query;
        String queryString = "";
        if (parameters != null && parameters.entrySet().size() > 0) {
            queryString = "from " + clazz.getName() + " as entity where " + getParametersAsString(parameters);
            query = entityManager.createQuery(queryString);
            setParameters(query, parameters);
        } else {
            queryString = "from " + clazz.getName();
            query = entityManager.createQuery(queryString);
        }
        if (offset != null)
            query.setFirstResult(offset);
        if (count != null)
            query.setMaxResults(count);
        log.info("EXECUTE QUERY: " + queryString);
        if (parameters != null)
            parameters.entrySet().forEach(e -> log.info("PARAMETER " + e.getKey() + " : " + e.getValue()));
        return query.getResultList();
    }

    public void setParameters(Query query, Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (entry.getKey().contains("id"))
                query.setParameter(entry.getKey().replace('.', '_'), Integer.valueOf(entry.getValue()));
            else
                query.setParameter(entry.getKey().replace('.', '_'), "%" + entry.getValue() + "%");
        }
    }

    public String getParametersAsString(Map<String, String> parameters) {
        String result = "";
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (entry.getKey().contains("id"))
                result = result + " entity." + entry.getKey() + " = " + " :" + entry.getKey().replace('.', '_') + " AND";
            else
                result = result + " entity." + entry.getKey() + " LIKE" + " :" + entry.getKey().replace('.', '_') + " AND";
        }
        return result.substring(0, result.length() - 3);
    }
}
