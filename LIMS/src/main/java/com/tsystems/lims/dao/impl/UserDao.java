package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.common.AbstractHibernateDao;
import com.tsystems.lims.dao.interfaces.IUserDao;
import com.tsystems.lims.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserDao extends AbstractHibernateDao<User> implements IUserDao {
    public UserDao() {
        setClazz(User.class);
    }

    public User getByName(String name) {
        List<User> users = getUsersByName(name);
        return users.size() > 0 ? users.get(0) : null;
    }

    public List<User> getUsersByName(String name) {
        return super.findAll(new HashMap<String, String>() {{
            if ((name != null)) {
                put("username", name);
            }
        }}, null, null);
    }
}


