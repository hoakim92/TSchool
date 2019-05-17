package com.tsystems.lims.service.common;

import java.util.List;
import java.util.Map;

public abstract class IOperationsService<T> {

    abstract protected T findOne(final Integer id);

    abstract protected List<T> findAll();

    abstract protected List<T> findAll(Integer offset, Integer count);

    abstract protected List<T> findAll(Map<String, String> parameters, Integer offset, Integer count);

    abstract protected void create(final T entity);

    abstract protected void create(final Iterable<T> entities);

    abstract protected void update(final T entity);

    abstract protected void update(final List<T> entities);

    abstract protected void delete(final T entity);

    abstract protected void deleteById(final Integer entityId);

}