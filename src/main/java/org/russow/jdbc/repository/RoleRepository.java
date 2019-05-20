package org.russow.jdbc.repository;

import org.russow.model.Role;

import java.util.List;

public interface RoleRepository<Entity extends Role> {

    List<Entity> getRoles();
}
