package com.libraryweb.app.controller;

import com.libraryweb.app.entity.Book;
import com.libraryweb.app.service.BookService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/search")
    public String getBooksByTitle(Model model, @Param("keyword") String keyword) {
        List<Book> books = bookService.getBooksByTitle(keyword);
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "search";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/history")
    public String getReservationHistory(Model model) {
        model.addAttribute("history", bookService.getAllReservations());
        return "history";
    }

    @GetMapping("/books/confirm/{id}")
    public String confirmationForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookByID(id));
        return "confirmReservation";
    }

    @PostMapping("/books/{id}")
    public String reserveBook(@PathVariable Long id,
                              Model model) {
        Book book = bookService.getBookByID(id);
        bookService.reserveBook(book);
        return "redirect:/history";
    }

    @RequestMapping("/")
    public String homePage() {
        return "index";
    }

    @RequestMapping("/history")
    public String historyPage() {
        return "history";
    }
}
