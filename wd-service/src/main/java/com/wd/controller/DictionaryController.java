package com.wd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wd.exception.FileStorageException;
import com.wd.model.response.SearchWordResponseDataBlk;
import com.wd.model.response.UploadFileResponseDataBlk;
import com.wd.service.IDictionaryService;
import com.wd.service.IFileService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/dictionary")
public class DictionaryController {
	
	@Autowired
	IDictionaryService dictionaryService;
	
	@Autowired
    private IFileService fileService;
	
	@GetMapping("search/{key}")
    public SearchWordResponseDataBlk uploadFile(@PathVariable("key") String key, @RequestParam("wordLimit") int wordLimit) {
		
		if(wordLimit <= 0) {
			// Default word limit.
			wordLimit = 30;
		}
		
		List<String> words = dictionaryService.getMatchingWords(key, wordLimit);
		SearchWordResponseDataBlk response = new SearchWordResponseDataBlk();
		response.setMatchingWords(words);
		return response;
    }
	
	@PostMapping("upload")
    public UploadFileResponseDataBlk uploadFile(@RequestParam MultipartFile file) throws FileStorageException {
		 String fileName = fileService.storeFile(file);
		 
		 dictionaryService.processFile(fileName);
		 
		 UploadFileResponseDataBlk response = new UploadFileResponseDataBlk();
	     response.setSuccess(true);
	     response.setMessage("Successfully uploaded the file");
	     return response;
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FileStorageException.class)
	public String handleValidationExceptions(FileStorageException ex) {
	    return ex.getMessage();
	}
}
