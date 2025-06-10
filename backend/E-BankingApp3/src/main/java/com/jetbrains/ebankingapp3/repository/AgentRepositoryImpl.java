package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Agent;
import com.jetbrains.ebankingapp3.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AgentRepositoryImpl implements AgentRepository{

    private final SessionFactory sessionFactory;

    public AgentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Agent> findAll() {
        String hql = "FROM Agent";
        return getSession().createQuery(hql, Agent.class).getResultList();
    }
    @Override
    public Optional<Agent> findById(Long id) {
        Agent agent = getSession().get(Agent.class, id);
        return Optional.ofNullable(agent);
    }
    @Override
    public Optional<Agent> findByMatricule(String matricule) {
        String hql = "FROM Agent a WHERE a.matricule = :matricule";
        Agent agent = getSession()
                .createQuery(hql, Agent.class)
                .setParameter("matricule", matricule)
                .uniqueResult();
        return Optional.ofNullable(agent);
    }

    @Override
    public Optional<Agent> findByEmail(String email) {
        String hql = "FROM Agent a WHERE a.email = :email";
        Agent agent = getSession()
                .createQuery(hql, Agent.class)
                .setParameter("email", email)
                .uniqueResult();
        return Optional.ofNullable(agent);
    }

    @Override
    public Optional<Agent> findByLogin(String login) {
        String hql = "FROM Agent a WHERE a.login = :login";
        Agent agent = getSession()
                .createQuery(hql, Agent.class)
                .setParameter("login", login)
                .uniqueResult();
        return Optional.ofNullable(agent);
    }
    @Override
    public Agent save(Agent agent) {
        Session session = getSession();
        if (agent.getId() == null || agent.getId() == 0) {
            session.persist(agent);
            return agent;
        } else {
            return (Agent) session.merge(agent);
        }
    }

    @Override
    public void deleteById(Long id) {
        Session session = getSession();
        Agent agent = session.get(Agent.class, id);
        if (agent != null) {
            session.remove(agent);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String hql = "SELECT count(a) FROM Agent a WHERE a.id = :id";
        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("id", id)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByMatricule(String matricule) {
        String hql = "SELECT count(a) FROM Agent a WHERE a.matricule = :matricule";
        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("matricule", matricule)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        String hql = "SELECT count(a) FROM Agent a WHERE a.email = :email";
        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("email", email)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByLogin(String login) {
        String hql = "SELECT count(a) FROM Agent a WHERE a.login = :login";
        Long count = getSession()
                .createQuery(hql, Long.class)
                .setParameter("login", login)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public List<Agent> findByBankId(Long bankId) {
        String hql = "FROM Agent a WHERE a.bank.id = :bankId";
        return getSession()
                .createQuery(hql, Agent.class)
                .setParameter("bankId", bankId)
                .getResultList();
    }

    @Override
    public List<Agent> findByRole(Role role) {
        String hql = "FROM Agent a WHERE a.role = :role";
        return getSession()
                .createQuery(hql, Agent.class)
                .setParameter("role", role)
                .getResultList();
    }
}
