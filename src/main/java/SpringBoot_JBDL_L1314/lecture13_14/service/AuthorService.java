package SpringBoot_JBDL_L1314.lecture13_14.service;

import SpringBoot_JBDL_L1314.lecture13_14.exceptions.BookException;
import SpringBoot_JBDL_L1314.lecture13_14.models.Author;
import SpringBoot_JBDL_L1314.lecture13_14.models.Book;
import SpringBoot_JBDL_L1314.lecture13_14.models.StatusCode;
import SpringBoot_JBDL_L1314.lecture13_14.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
     AuthorRepository authorRepository;



    public Author saveOrUpdate(Author author){
        return authorRepository.save(author);
    }


    public Optional<Author> findByEmail(Author author){
        return authorRepository.findByEmail(author.getEmail());
    }

    public List<Author> fetchAllAuthors(){
        return authorRepository.findAll();
    }

    public List<Book> getBooksList(Integer id){

        Optional<Author> optionalAuthor= authorRepository.findById(id);
        if(optionalAuthor.isEmpty())
            throw new BookException(StatusCode.CHEGG_07);

        return optionalAuthor.get().getBookList();

    }





}
