package SpringBoot_JBDL_L1314.lecture13_14.repository;

import SpringBoot_JBDL_L1314.lecture13_14.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {



    Optional<Book> findByIsbn(String isbn);


    Optional<List<Book>> findByName(String name);
}
