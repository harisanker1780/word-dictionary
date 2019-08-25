package com.wd.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.wd.config.FileProperties;
import com.wd.exception.FileStorageException;
import com.wd.service.IFileService;
import com.wd.util.FileUtil;

@Service
public class FileService implements IFileService {
	
 	private Path fileStorageLocation;

    @Autowired
    private FileProperties fileProperties;

    @Override
    public String storeFile(MultipartFile file) throws FileStorageException {
        // Normalize file name
        fileStorageLocation = createFileStorageLocation();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
        	if(FileUtil.fileSizeInMB(file) > fileProperties.getMaxSizeInMB())  {
        		throw new FileStorageException("File size should not exceed " + fileProperties.getMaxSizeInMB() + " MB.");
        	}
        	
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            String extension = FileUtil.getExtension(fileName);
            if(!isExtensionSupported(extension)) {
            	throw new FileStorageException("File with extension " + extension + " not supported.");
            }

            fileName =  FileUtil.createUniqueFileName(fileName);

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    @Override
	public File getFile(String fileName) throws FileStorageException {
    	fileStorageLocation = createFileStorageLocation();
    	Path targetLocation = this.fileStorageLocation.resolve(fileName);
		return targetLocation.toFile();
	}
    
    @Override
	public File createFile(String fileName) throws FileStorageException, IOException {
    	fileStorageLocation = createFileStorageLocation();
    	Path targetLocation = fileStorageLocation.resolve(fileName);
    	File file = targetLocation.toFile();
    	if(!file.exists()) {
    		file.createNewFile();
    	}
		return file;
	}
    
    @Override
	public void deleteFile(String fileName) {
    	try {
			fileStorageLocation = createFileStorageLocation();
			Path targetLocation = fileStorageLocation.resolve(fileName);
			File file = targetLocation.toFile();
	    	if(file.exists()) {
	    		file.delete();
	    	}
		} catch (FileStorageException e) {
			e.printStackTrace();
		}
	}

    
    private Path createFileStorageLocation() throws FileStorageException {
        this.fileStorageLocation = Paths.get(fileProperties.getStorageDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
        return fileStorageLocation;
    }
    
    
    private boolean isExtensionSupported(String extension) {
    	String[] allowedExtensions = fileProperties.getAllowedExtensions();
    	extension = extension.substring(1, extension.length());
    	for(String e : allowedExtensions) {
    		if(extension.toLowerCase().equals(e)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}
