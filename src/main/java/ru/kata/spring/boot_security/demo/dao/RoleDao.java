package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDao {

    private EntityManager entityManager;

    public List<Role> getRolesList() {
        String jpql = "SELECT c FROM Role c";
        TypedQuery<Role> query = entityManager.createQuery(jpql, Role.class);
        return query.getResultList();
    }

    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }
}
