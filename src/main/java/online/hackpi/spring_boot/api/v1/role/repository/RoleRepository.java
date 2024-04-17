package online.hackpi.spring_boot.api.v1.role.repository;

import online.hackpi.spring_boot.api.v1.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findRoleByRoleName(String name);
}
