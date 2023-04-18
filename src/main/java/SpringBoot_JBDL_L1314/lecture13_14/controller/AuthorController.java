package SpringBoot_JBDL_L1314.lecture13_14.controller;

import SpringBoot_JBDL_L1314.lecture13_14.models.Author;
import SpringBoot_JBDL_L1314.lecture13_14.models.Book;
import SpringBoot_JBDL_L1314.lecture13_14.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @GetMapping(value = "/authors",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Author>> fetchAuthors(){
        log.info("Request Received for fetching all Authors ");
        return new ResponseEntity<>(authorService.fetchAllAuthors(),HttpStatus.OK);
    }

    @GetMapping(value = "author/books", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> fetchAllBooksOfAuthor(@RequestParam Integer id){
        log.info("Request Received for fetching all books of author with author id= ");
        /**
         * this should create a stack overflow hell
         */
        return new ResponseEntity<>(authorService.getBooksList(id), HttpStatus.OK);
    }
}
