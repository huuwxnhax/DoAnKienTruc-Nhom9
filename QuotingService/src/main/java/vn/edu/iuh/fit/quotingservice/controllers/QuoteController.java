package vn.edu.iuh.fit.quotingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.quotingservice.entity.Quote;
import vn.edu.iuh.fit.quotingservice.service.QuoteService;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuote(@PathVariable Long id) {
        Quote quote = quoteService.getQuote(id);
        if (quote != null) {
            return new ResponseEntity<>(quote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Quote not found.", HttpStatus.NOT_FOUND);
        }
    }
    

    @GetMapping
    public ResponseEntity<?> getAllQuotes() {
        List<Quote> quotes = quoteService.getAllQuotes();
        if (!quotes.isEmpty()) {
            return new ResponseEntity<>(quotes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No quotes found.", HttpStatus.NOT_FOUND);
        }
    }
}
