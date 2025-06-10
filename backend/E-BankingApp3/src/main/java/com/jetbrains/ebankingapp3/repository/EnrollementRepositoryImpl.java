package com.jetbrains.ebankingapp3.repository;

import com.jetbrains.ebankingapp3.model.Agent;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.model.Enrollement;
import com.jetbrains.ebankingapp3.model.StatutEnrollement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrollementRepositoryImpl implements EnrollementRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Enrollement> enrollementRowMapper = (rs, rowNum) -> {
        Enrollement enrollement = new Enrollement();
        enrollement.setId(rs.getLong("enrollement_id"));

        Client client= new Client();
        client.setClientId(rs.getInt("client_id"));
        enrollement.setClient(client);

        Agent agent= new Agent();
        agent.setId(rs.getLong("agent_id"));
        enrollement.setAgent(agent);

        enrollement.setDateEnrollement(rs.getTimestamp("date_enrollement").toLocalDateTime());
        enrollement.setReference(rs.getString("reference"));
        //enrollement.setStatut(StatutEnrollement.valueOf(rs.getString("statut")));
        enrollement.setStatut(StatutEnrollement.fromString(rs.getString("statut")));

        String docs = rs.getString("documents");
        if (docs != null && !docs.isEmpty()) {
            enrollement.setDocuments(Arrays.asList(docs.split(",")));
        } else {
            enrollement.setDocuments(new ArrayList<>());
        }


        return enrollement;
    };

    @Autowired
    public EnrollementRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Enrollement> findAll() {
        String sql = "SELECT * FROM enrollement";
        return jdbcTemplate.query(sql, enrollementRowMapper);
    }

    @Override
    public Optional<Enrollement> findById(Long id) {
        try {
            String sql = "SELECT * FROM enrollement WHERE enrollement_id = ?";
            Enrollement enrollement = jdbcTemplate.queryForObject(sql, enrollementRowMapper, id);
            return Optional.ofNullable(enrollement);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Enrollement> findByClientId(Long clientId) {
        String sql = "SELECT * FROM enrollement WHERE client_id = ?";
        return jdbcTemplate.query(sql, enrollementRowMapper, clientId);
    }

    @Override
    public List<Enrollement> findByAgentId(Long agentId) {
        String sql = "SELECT * FROM enrollement WHERE agent_id = ?";
        return jdbcTemplate.query(sql, enrollementRowMapper, agentId);
    }

    @Override
    public List<Enrollement> findByStatut(StatutEnrollement statut) {
        String sql = "SELECT * FROM enrollement WHERE statut = ?";
        return jdbcTemplate.query(sql, enrollementRowMapper, statut.toString());
    }

    @Override
    public Enrollement save(Enrollement enrollement) {
        if (enrollement.getId() == null) {
            return insertEnrollement(enrollement);
        } else {
            return updateEnrollement(enrollement);
        }
    }

    private Enrollement insertEnrollement(Enrollement enrollement) {
        String sql = "INSERT INTO enrollement (client_id, agent_id, date_enrollement, reference, statut, documents) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING enrollement_id";

        String[] documentsArray = enrollement.getDocuments().toArray(new String[0]);
        Long enrollementId = jdbcTemplate.queryForObject(sql, Long.class,
                enrollement.getClient().getClientId(),
                enrollement.getAgent().getId(),
                Timestamp.valueOf(enrollement.getDateEnrollement()),
                enrollement.getReference(),
                enrollement.getStatut().toString(),
                documentsArray);

        enrollement.setId(enrollementId);
        return enrollement;
    }

    private Enrollement updateEnrollement(Enrollement enrollement) {
        String sql = "UPDATE enrollement SET client_id = ?, agent_id = ?, date_enrollement = ?, " +
                "reference = ?, statut = ? WHERE enrollement_id = ?";

        jdbcTemplate.update(sql,
                enrollement.getClient().getClientId(),
                enrollement.getAgent().getId(),
                Timestamp.valueOf(enrollement.getDateEnrollement()),
                enrollement.getReference(),
                enrollement.getStatut().toString(),
                enrollement.getId());

        return enrollement;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM enrollement WHERE enrollement_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM enrollement WHERE enrollement_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByClientIdAndStatut(Long clientId, StatutEnrollement statut) {
        String sql = "SELECT COUNT(*) FROM enrollement WHERE client_id = ? AND statut = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, clientId, statut.toString());
        return count != null && count > 0;
    }

    @Override
    public Optional<Enrollement> findByReference(String reference) {
        try {
            String sql = "SELECT * FROM enrollement WHERE reference = ?";
            Enrollement enrollement = jdbcTemplate.queryForObject(sql, enrollementRowMapper, reference);
            return Optional.ofNullable(enrollement);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Enrollement> findTop5ByOrderByDateEnrollementDesc() {
        String sql = "SELECT * FROM enrollement ORDER BY date_enrollement DESC LIMIT 5";
        return jdbcTemplate.query(sql, enrollementRowMapper);
    }

    @Override
    public List<Enrollement> findRecent(int limit) {
        String sql = "SELECT * FROM enrollement ORDER BY date_enrollement LIMIT ?";
        return jdbcTemplate.query(sql, enrollementRowMapper, limit);
    }
}
