package senac.tsi.books.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}


