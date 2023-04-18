package SpringBoot_JBDL_L1314.lecture13_14.repository;


import SpringBoot_JBDL_L1314.lecture13_14.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {


    /**
     * Writing custom Queries using JPQL
     * select * from author where email = ?
     */

    Optional<Author> findByEmail(String email);

}