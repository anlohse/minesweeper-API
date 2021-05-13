package com.anlohse.minesweeper.commons.repositories;

import com.anlohse.minesweeper.commons.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationCode(String activationCode);

    Optional<User> findByRecoveryCode(String recoveryCode);

}
