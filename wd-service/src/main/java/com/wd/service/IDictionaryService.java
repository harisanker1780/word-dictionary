package com.wd.service;

import java.util.List;

public interface IDictionaryService {
	
	void processFile(String fileName);
	
	List<String> getMatchingWords(String key, int wordLimit);
}
