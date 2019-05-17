package com.tsystems.lims.service.common;

import com.tsystems.lims.dao.common.IOperations;

import java.util.List;
import java.util.Map;

public abstract class AbstractService<T> extends IOperationsService<T> {

    @Override
    protected T findOne(final Integer id) {
        return getDao().findOne(id);
    }

    @Override
    protected List<T> findAll(Map<String, String> parameters,  Integer offset, Integer count) {
        return getDao().findAll(parameters, offset, count);
    }

    @Override
    protected List<T> findAll(Integer offset, Integer count) {
        return getDao().findAll(null, offset, count);
    }

    @Override
    protected List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    protected void create(final T entity) {
        getDao().create(entity);
    }

    @Override
    protected void create(final Iterable<T> entities) {
        getDao().create(entities);
    }

    @Override
    protected void update(final T entity) {
        getDao().update(entity);
    }

    @Override
    protected void delete(final T entity) {
        getDao().delete(entity);
    }

    @Override
    protected void deleteById(final Integer entityId) {
        getDao().deleteById(entityId);
    }

    protected abstract IOperations<T> getDao();

}