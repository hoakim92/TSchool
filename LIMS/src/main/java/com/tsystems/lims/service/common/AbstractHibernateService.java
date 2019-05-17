package com.tsystems.lims.service.common;

import com.tsystems.lims.dao.common.IOperations;
import com.tsystems.lims.models.Prescription;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public abstract class AbstractHibernateService<T> extends AbstractService<T> {

    @Override
    protected T findOne(final Integer id) {
        return super.findOne(id);
    }

    @Override
    protected List<T> findAll() {
        return super.findAll();
    }

    @Override
    protected List<T> findAll(Map<String, String> parameters, Integer offset, Integer count) {
        return super.findAll(parameters, offset, count);
    }

    @Override
    protected void create(final T entity) { super.create(entity); }

    @Override
    protected void create(final Iterable<T> entity) { super.create(entity); }

    @Override
    protected void update(final T entity) {
        super.update(entity);
    }

    @Override
    protected void update(final List<T> entities) {
        entities.forEach(super::update);
    }

    @Override
    protected void delete(final T entity) {
        super.delete(entity);
    }

    @Override
    protected void deleteById(final Integer entityId) {
        super.deleteById(entityId);
    }


}