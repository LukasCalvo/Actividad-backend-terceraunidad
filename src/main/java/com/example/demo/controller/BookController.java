package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management System", description = "Operaciones relacionadas con libros")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @Operation(summary = "View a list of available books")
    public List<Book> getAllBooks() {
        log.info("Obteniendo todos los libros.");
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by Id")
    public Book getBookById(@PathVariable Long id) {
        log.info("Obteniendo el libro con la id: {}", id);
        return bookService.getBookById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new book")
    public Book createBook(@RequestBody Book book) {
        log.info("Se ha agregado el siguiente libro:\nTitulo: {}\nautor: {}", book.getTitle(), book.getAuthor());
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book existingBook = bookService.getBookById(id);
        if (existingBook != null) {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            return bookService.saveBook(existingBook);
        }
        log.info("Se ha modificado el libro con la id: {}", id);
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        log.info("Se ha borrado el libro con la id: {}", id);
    }
}