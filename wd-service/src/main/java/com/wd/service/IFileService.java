package com.wd.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.wd.exception.FileStorageException;

public interface IFileService {
	
	String storeFile(MultipartFile file) throws FileStorageException;

	File getFile(String fileName) throws FileStorageException;
	
	File createFile(String fileName) throws FileStorageException, IOException;
	
	void deleteFile(String fileName);
}
