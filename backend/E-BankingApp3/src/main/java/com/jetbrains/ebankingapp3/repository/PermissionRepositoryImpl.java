package com.jetbrains.ebankingapp3.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.security.Permission;
import java.util.List;
import java.util.Optional;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public PermissionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Permission save(Permission permission) {
        getCurrentSession().saveOrUpdate(permission);
        return permission;
    }

    @Override
    public Optional<Permission> findById(Long id) {
        Permission permission = getCurrentSession().get(Permission.class, id);
        return Optional.ofNullable(permission);
    }

//    @Override
//    public Optional<Permission> findByName(String name) {
//        Query<Permission> query = getCurrentSession().createQuery(
//                "FROM Permission WHERE name = :name", Permission.class);
//        query.setParameter("name", name);
//        return query.uniqueResultOptional();
//    }

//    @Override
//    public List<Permission> findAll() {
//        return getCurrentSession().createQuery("FROM Permission", Permission.class).list();
//    }

    @Override
    public void delete(Permission permission) {
        getCurrentSession().remove(permission);
    }
}