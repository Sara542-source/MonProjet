package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BankRepositoryImpl implements BankRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Bank> bankRowMapper = (rs, rowNum) -> {
        Bank bank = new Bank();
        bank.setId(rs.getLong("bank_id"));
        bank.setCode(rs.getString("code"));
        bank.setName(rs.getString("name"));
        bank.setAddress(rs.getString("address"));
        bank.setPhone(rs.getString("phone"));
        bank.setEmail(rs.getString("email"));
        return bank;
    };

    @Autowired
    public BankRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Bank> findAll() {
        String sql = "SELECT * FROM bank";
        return jdbcTemplate.query(sql, bankRowMapper);
    }

    @Override
    public Optional<Bank> findById(Long id) {
        try {
            String sql = "SELECT * FROM bank WHERE bank_id = ?";
            Bank bank = jdbcTemplate.queryForObject(sql, bankRowMapper, id);
            return Optional.ofNullable(bank);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Bank> findByCode(String code) {
        try {
            String sql = "SELECT * FROM bank WHERE code = ?";
            Bank bank = jdbcTemplate.queryForObject(sql, bankRowMapper, code);
            return Optional.ofNullable(bank);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Bank save(Bank bank) {
        if (bank.getId() == null) {
            return insertBank(bank);
        } else {
            return updateBank(bank);
        }
    }

    private Bank insertBank(Bank bank) {
        String sql = "INSERT INTO bank (code, name, address, phone, email) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING bank_id";

        Long bankId = jdbcTemplate.queryForObject(sql, Long.class,
                bank.getCode(),
                bank.getName(),
                bank.getAddress(),
                bank.getPhone(),
                bank.getEmail());

        bank.setId(bankId);
        return bank;
    }

    private Bank updateBank(Bank bank) {
        String sql = "UPDATE bank SET code = ?, name = ?, address = ?, phone = ?, email = ? " +
                "WHERE bank_id = ?";

        jdbcTemplate.update(sql,
                bank.getCode(),
                bank.getName(),
                bank.getAddress(),
                bank.getPhone(),
                bank.getEmail(),
                bank.getId());

        return bank;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM bank WHERE bank_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM bank WHERE bank_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCode(String code) {
        String sql = "SELECT COUNT(*) FROM bank WHERE code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code);
        return count != null && count > 0;
    }
}
