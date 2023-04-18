package SpringBoot_JBDL_L1314.lecture13_14.controller;

import SpringBoot_JBDL_L1314.lecture13_14.models.Book;
import SpringBoot_JBDL_L1314.lecture13_14.requests.CreateBookRequestDto;
import SpringBoot_JBDL_L1314.lecture13_14.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> createABook( @Valid @RequestBody CreateBookRequestDto book){
        log.info("Request Received {} " , book);
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }


    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> fetchAllBooks(){
        log.info("Request Received for fetching all books  ");
        /**
         * this should create a stack overflow hell
         */
        return new ResponseEntity<>(bookService.fetchAllBooks(), HttpStatus.OK);
    }

    @GetMapping(value = "/bookbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<List<Book>>>fetchBookByName(@RequestParam(name = "name") String name){
        log.info("Request Received for fetching book by name");
        log.info("the name received{}",name);
        /**
         * this should create a stack overflow hell
         */

        return new ResponseEntity<>(bookService.findByName(name.toUpperCase()), HttpStatus.OK);
    }


}
