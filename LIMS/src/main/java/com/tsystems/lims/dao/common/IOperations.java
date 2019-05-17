package com.tsystems.lims.dao.common;

import java.util.List;
import java.util.Map;

public interface IOperations<T> {

    T findOne(final Integer id);

    List<T> findAll();

    List<T> findAll(Integer offset, Integer count);

    List<T> findAll(Map<String, String> parameters, Integer offset, Integer count);

    void create(final T entity);

    void create(final Iterable<T> entities);

    void update(final T entity);

    void update(final List<T> entities);

    void delete(final T entity);

    void deleteById(final Integer entityId);

}
