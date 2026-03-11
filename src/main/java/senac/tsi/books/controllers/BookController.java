package senac.tsi.books.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senac.tsi.books.entities.Book;
import senac.tsi.books.exceptions.BookNotFoundException;
import senac.tsi.books.repositories.BookRepository;

import java.net.URI;
import java.util.List;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book newBook){
        bookRepository.save(newBook);
        return ResponseEntity.created(
                        URI.create("/books/"+ newBook.getId()))
                .body(newBook);

    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBook(@PathVariable long id){
        var book = bookRepository.findById(id).orElse(null);
        if(book == null)
            return ResponseEntity.notFound().build();

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
