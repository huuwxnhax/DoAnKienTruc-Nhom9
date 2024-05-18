package vn.edu.iuh.fit.quotingservice.service;

import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.quotingservice.entity.Device;
import vn.edu.iuh.fit.quotingservice.entity.Quote;

import java.util.List;

@Service
public interface QuoteService {
    public Quote getQuote(Long id);
    public void createQuote(Device device);
    public List<Quote> getAllQuotes();
}
