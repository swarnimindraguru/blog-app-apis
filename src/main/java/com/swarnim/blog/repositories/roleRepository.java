package com.swarnim.blog.repositories;

import com.swarnim.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface roleRepository extends JpaRepository<Role, Integer> {
    @Override
    Optional<Role> findById(Integer integer);

    @Override
    <S extends Role> List<S> saveAll(Iterable<S> entities);
}
