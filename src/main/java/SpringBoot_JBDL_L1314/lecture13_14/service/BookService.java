package SpringBoot_JBDL_L1314.lecture13_14.service;

import SpringBoot_JBDL_L1314.lecture13_14.exceptions.BookException;
import SpringBoot_JBDL_L1314.lecture13_14.exceptions.BookExistsException;
import SpringBoot_JBDL_L1314.lecture13_14.exceptions.BookNotFoundException;
import SpringBoot_JBDL_L1314.lecture13_14.exceptions.ConnectionErrorException;
import SpringBoot_JBDL_L1314.lecture13_14.models.Author;
import SpringBoot_JBDL_L1314.lecture13_14.models.Book;
import SpringBoot_JBDL_L1314.lecture13_14.models.StatusCode;
import SpringBoot_JBDL_L1314.lecture13_14.repository.BookRepository;
import SpringBoot_JBDL_L1314.lecture13_14.requests.CreateBookRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BookService {
private static Logger log= LoggerFactory.getLogger(BookService.class);
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;


    public Book createBook(CreateBookRequestDto userRequestDto) {
        Book  book = userRequestDto.toBook();

        Optional<Book> exisitingBook = bookRepository.findByIsbn(book.getIsbn());
        if(exisitingBook.isPresent()){
            throw new BookExistsException();
        }

        /**
         * create a new author is exisiting not present
         */
        try {
            Optional<Author> existingAuthor = authorService.findByEmail(book.getAuthor1());
            if(existingAuthor.isEmpty()) {
                Author author = authorService.saveOrUpdate(book.getAuthor1()); //saveOrUpdate is a JpaRepository<Author, Integer> method which we have used instead of defining our own method
                book.setAuthor1(author);
            } else {
                book.setAuthor1(existingAuthor.get());
            }
        } catch (Exception e){
            throw new ConnectionErrorException();
        }

        return saveOrUpdate(book);
    }


    public Book saveOrUpdate(Book book){
        return bookRepository.save(book);
    }

    public List<Book> fetchAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    public Optional<List<Book>> findByName(String name) {
        System.out.println("fetch all books by name=--------------------------------"+name);
       Optional<List<Book>> optionalBookList = bookRepository.findByName(name);
       List<Book> book=optionalBookList.get();

       if(book.isEmpty())
           throw new BookException(StatusCode.CHEGG_06);

        System.out.println("book found___________________________________________"+book);
       return optionalBookList;
    }
}