package com.wd.service.impl.trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wd.exception.FileStorageException;
import com.wd.service.IDictionaryService;
import com.wd.service.IFileService;

@Service
public class TrieDictionaryService implements IDictionaryService {
	
	@Autowired
    private IFileService fileService;
	
	@Override
	public void processFile(String fileName) {
		try {
			File file = fileService.getFile(fileName);
			if(file.exists()) {
				
			   File targetFile = fileService.createFile("trie.json");
			   Trie trie = buildTrie(targetFile); 
			   
			   BufferedReader br = new BufferedReader(new FileReader(file));		
			   String contentLine = br.readLine();
			   while (contentLine != null) {
				  contentLine = contentLine.replaceAll("[^a-zA-Z]", " ");
			      String[] words = contentLine.split(" ");
			      for(String word : words) {
			    	  word = word.trim().toLowerCase();
			    	  if(word.length() > 0) {
			    		  trie.addKey(word);
			    	  }
			      }
			      
			      contentLine = br.readLine();
			   }
			   
			   br.close();
			  
			   ObjectMapper mapper = new ObjectMapper();
			   String json = mapper.writeValueAsString(trie.getRoot());
			   BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
			   writer.write(json);
			   writer.close();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			fileService.deleteFile(fileName);
		}
	}

	@Override
	public List<String> getMatchingWords(String key, int wordLimit) {
		File targetFile;
		try {
			targetFile = fileService.createFile("trie.json");
			Trie trie = buildTrie(targetFile); 
			return trie.matchingWords(key, wordLimit);
		} catch (FileStorageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Trie buildTrie(File targetFile) throws FileStorageException, IOException {
	   Trie trie; 
	   ObjectMapper mapper = new ObjectMapper();
	   if(targetFile.length() > 0) {
		   String content = new String(Files.readAllBytes(targetFile.toPath()));
		   TrieNode root = mapper.readValue(content, TrieNode.class);
		   trie = new Trie(root);
	   }
	   else { 
		   trie = new Trie();
	   }
	   
	   return trie;
	}
}
