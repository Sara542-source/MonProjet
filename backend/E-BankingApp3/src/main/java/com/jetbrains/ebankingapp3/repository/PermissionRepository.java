package com.jetbrains.ebankingapp3.repository;

import java.security.Permission;
import java.util.List;
import java.util.Optional;

public interface PermissionRepository {
    Permission save(Permission permission);
    Optional<Permission> findById(Long id);
  //  Optional<Permission> findByName(String name);
   // List<Permission> findAll();
    void delete(Permission permission);
}
