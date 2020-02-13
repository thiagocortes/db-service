package com.cortes.stock.dbservice.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cortes.stock.dbservice.dto.QuoteDTO;
import com.cortes.stock.dbservice.model.Quote;
import com.cortes.stock.dbservice.repository.QuotesRepository;

@RestController
@RequestMapping("/rest/db")
public class DBServiceResource {

	@Autowired
	private QuotesRepository quotesRepository;

	@GetMapping("/{username}")
	public List<String> getQuotes(@PathVariable("username") final String username){
		return getQuotesByUserName(username);
	}
	
	@PostMapping("/add")
	public List<String> getQuotes(@RequestBody final QuoteDTO dto){
		dto.getQuotes().stream()
		.map(dt-> new Quote(dto.getUsername(), dt))
		.forEach(dt-> quotesRepository.save(dt));
		return getQuotesByUserName(dto.getUsername());
	}
	
	private List<String> getQuotesByUserName(final String username){
		return quotesRepository.findByUserName(username)
				.stream()
				.map(Quote::getQuote)
				.collect(Collectors.toList());
	}
}
