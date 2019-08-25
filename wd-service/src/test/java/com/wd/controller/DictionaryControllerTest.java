package com.wd.controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.wd.model.response.SearchWordResponseDataBlk;
import com.wd.model.response.UploadFileResponseDataBlk;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DictionaryControllerTest extends BaseControllerTest {

	public DictionaryControllerTest() {
		super("/dictionary/");
	}

	@Test
	public void testUploadFile() {
		FileInputStream fis;
		try {
			File input = new ClassPathResource("input.txt").getFile();
			fis = new FileInputStream(input);
			MockMultipartFile multipartFile = new MockMultipartFile("file", "input.txt", "text/plain", fis);
			UploadFileResponseDataBlk response = postMultipartData("upload", multipartFile, UploadFileResponseDataBlk.class);
			fis.close();
			assertEquals(true, response.isSuccess());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchContains() {
		try {
			SearchWordResponseDataBlk response = get("search/th?wordLimit=100", SearchWordResponseDataBlk.class);
			List<String> matchingWords = response.getMatchingWords();
			assertEquals(true, matchingWords.size() > 0);
			assertEquals(true, matchingWords.contains("the"));
			assertEquals(true, matchingWords.contains("they"));
			assertEquals(true, matchingWords.contains("their"));
			assertEquals(true, matchingWords.contains("theory"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchNotContains() {
		try {
			SearchWordResponseDataBlk response = get("search/a?wordLimit=100", SearchWordResponseDataBlk.class);
			List<String> matchingWords = response.getMatchingWords();
			assertEquals(true, matchingWords.size() > 0);
			assertEquals(true, !matchingWords.contains("theory"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchSingleWord() {
		try {
			SearchWordResponseDataBlk response = get("search/another?wordLimit=1", SearchWordResponseDataBlk.class);
			List<String> matchingWords = response.getMatchingWords();
			assertEquals(true, matchingWords.size() > 0);
			assertEquals(true, matchingWords.get(0).equals("another"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
