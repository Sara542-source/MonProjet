package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//c est pour la banck et la gent
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final SessionFactory sessionFactory;

    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    @Override
    public List<Account> findAll() {
        String hql = "FROM Account";
        return getSession().createQuery(hql, Account.class).getResultList();
    }
    @Override
    public Optional<Account> findById(Long accountId) {
        Account account = getSession().get(Account.class, accountId);
        return Optional.ofNullable(account);
    }
    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        String hql = "FROM Account a WHERE a.accountNumber = :accountNumber";
        Account result = getSession()
                .createQuery(hql, Account.class)
                .setParameter("accountNumber", accountNumber)
                .uniqueResult();
        return Optional.ofNullable(result);
    }
    @Override
    public List<Account> findByUserId(Long clientId) {
        String hql = "FROM Account a WHERE a.client.id = :clientId";
        return getSession()
                .createQuery(hql, Account.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }
    @Override
    public List<Account> findByBranchId(Long branchId) {
        String hql = "FROM Account a WHERE a.branchId = :branchId";
        return getSession()
                .createQuery(hql, Account.class)
                .setParameter("branchId", branchId)
                .getResultList();
    }

    @Override
    public Account save(Account account) {
        Session session = getSession();
        if (account.getId() == 0) { // 0 signifie que l'account n'a pas encore d'id assigné par la base
            session.persist(account);
            return account;
        } else {
            return (Account) session.merge(account);
        }
    }

    @Override
    public void deleteById(Long accountId) {
        Session session = getSession();
        Account account = session.get(Account.class, accountId);
        if (account != null) {
            session.remove(account);
        }
    }

    @Override
    public boolean existsById(Long accountId) {
        String hql = "SELECT count(a) FROM Account a WHERE a.id = :accountId";
        Long count = getSession().createQuery(hql, Long.class)
                .setParameter("accountId", accountId)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        String hql = "SELECT count(a) FROM Account a WHERE a.accountNumber = :accountNumber";
        Long count = getSession().createQuery(hql, Long.class)
                .setParameter("accountNumber", accountNumber)
                .uniqueResult();
        return count != null && count > 0;
    }
    @Override
    public List<Account> findByUserIdAndStatus(Long clientId, String status) {
        String hql = "FROM Account a WHERE a.client.id = :clientId AND a.status = :status";
        return getSession()
                .createQuery(hql, Account.class)
                .setParameter("clientId", clientId)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<AccountTypeCount> countAccountsByType() {
        return List.of();
    }

    @Override
    public List<Account> findByUserIdAndAccountType(Long clientId, String accountType) {
        String hql = "FROM Account a WHERE a.client.id = :clientId AND a.accountType = :accountType";
        return getSession()
                .createQuery(hql, Account.class)
                .setParameter("clientId", clientId)
                .setParameter("accountType", accountType)
                .getResultList();
    }

    @Override
    public void updateBalance(Long accountId, Double newBalance) {
        Session session = getSession();
        Account account = session.get(Account.class, accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }
        account.setBalance(newBalance);
        account.setLastUpdated(java.time.LocalDateTime.now());
        session.merge(account);
    }

}
