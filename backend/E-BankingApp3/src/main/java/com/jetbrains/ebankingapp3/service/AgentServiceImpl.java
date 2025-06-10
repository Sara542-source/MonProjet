package com.jetbrains.ebankingapp3.service;

import com.jetbrains.ebankingapp3.model.Agent;
import com.jetbrains.ebankingapp3.model.Role;
import com.jetbrains.ebankingapp3.repository.AgentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {


        private final AgentRepository agentRepository;
        private final PasswordEncoder passwordEncoder;
        @Autowired
        public AgentServiceImpl(AgentRepository agentRepository, PasswordEncoder passwordEncoder) {
            this.agentRepository = agentRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public List<Agent> getAllAgents() {
            return agentRepository.findAll();
        }

        @Override
        public Optional<Agent> getAgentById(Long id) {
            return agentRepository.findById(id);
        }

        @Override
        public Optional<Agent> getAgentByMatricule(String matricule) {
            return agentRepository.findByMatricule(matricule);
        }

        @Override
        public Optional<Agent> getAgentByEmail(String email) {
            return agentRepository.findByEmail(email);
        }

        @Override
        public Optional<Agent> getAgentByLogin(String login) {
            return agentRepository.findByLogin(login);
        }

        @Override
        @Transactional
        public Agent createAgent(Agent agent) {
            // Validate unique constraints
            if (agentRepository.existsByMatricule(agent.getMatricule())) {
                throw new IllegalArgumentException("Matricule already exists");
            }
            if (agentRepository.existsByEmail(agent.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            if (agentRepository.existsByLogin(agent.getLogin())) {
                throw new IllegalArgumentException("Login already exists");
            }

            // Additional validation
            if (agent.getBank() == null || agent.getBank().getId() == null) {
                throw new IllegalArgumentException("Agent must be associated with a bank");
            }

            return agentRepository.save(agent);
        }

        @Override
        @Transactional
        public Optional<Agent> updateAgent(Long id, Agent agentDetails) {
            return agentRepository.findById(id).map(existingAgent -> {
                // Check if matricule is being changed
                if (!existingAgent.getMatricule().equals(agentDetails.getMatricule()) &&
                        agentRepository.existsByMatricule(agentDetails.getMatricule())) {
                    throw new IllegalArgumentException("Matricule already exists");
                }

                // Check if email is being changed
                if (!existingAgent.getEmail().equals(agentDetails.getEmail()) &&
                        agentRepository.existsByEmail(agentDetails.getEmail())) {
                    throw new IllegalArgumentException("Email already exists");
                }

                // Check if login is being changed
                if (!existingAgent.getLogin().equals(agentDetails.getLogin()) &&
                        agentRepository.existsByLogin(agentDetails.getLogin())) {
                    throw new IllegalArgumentException("Login already exists");
                }

                // Update fields
                if (agentDetails.getMatricule() != null) {
                    existingAgent.setMatricule(agentDetails.getMatricule());
                }
                if (agentDetails.getNom() != null) {
                    existingAgent.setNom(agentDetails.getNom());
                }
                if (agentDetails.getPrenom() != null) {
                    existingAgent.setPrenom(agentDetails.getPrenom());
                }
                if (agentDetails.getEmail() != null) {
                    existingAgent.setEmail(agentDetails.getEmail());
                }
                if (agentDetails.getTelephone() != null) {
                    existingAgent.setTelephone(agentDetails.getTelephone());
                }
                if (agentDetails.getLogin() != null) {
                    existingAgent.setLogin(agentDetails.getLogin());
                }
                if (agentDetails.getPassword() != null) {
                    existingAgent.setPassword(agentDetails.getPassword());
                }
                if (agentDetails.getRole() != null) {
                    existingAgent.setRole(agentDetails.getRole());
                }
                if (agentDetails.getBank() != null && agentDetails.getBank().getId() != null) {
                    existingAgent.setBank(agentDetails.getBank());
                }

                return agentRepository.save(existingAgent);
            });
        }

        @Override
        @Transactional
        public boolean deleteAgent(Long id) {
            if (agentRepository.existsById(id)) {
                agentRepository.deleteById(id);
                return true;
            }
            return false;
        }

        @Override
        public boolean agentExists(Long id) {
            return agentRepository.existsById(id);
        }

        @Override
        public boolean matriculeExists(String matricule) {
            return agentRepository.existsByMatricule(matricule);
        }

        @Override
        public boolean emailExists(String email) {
            return agentRepository.existsByEmail(email);
        }

        @Override
        public boolean loginExists(String login) {
            return agentRepository.existsByLogin(login);
        }

        @Override
        public List<Agent> getAgentsByBanque(Long banqueId) {
            return agentRepository.findByBankId(banqueId);
        }

    @Override
    public List<Agent> getAgentsByRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Rôle inconnu : null");
        }
        return agentRepository.findByRole(role);
    }
    @Override
        public Optional<Agent> authenticateAgent(String login, String password) {
            return agentRepository.findByLogin(login)
                    .filter(agent -> passwordEncoder.matches(password, agent.getPassword()));
        }

        public Optional<Agent> getCurrentAgent(HttpSession session) {
            return Optional.ofNullable((Agent) session.getAttribute("loggedInAgent"));
        }

        public boolean isAuthenticated(HttpSession session) {
            return session.getAttribute("loggedInAgent") != null;
        }
    }