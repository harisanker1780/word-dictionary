package com.wd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	public static long fileSizeInMB(MultipartFile file) {
		long fileSizeInBytes = file.getSize();
    	long fileSizeInKB = fileSizeInBytes / 1024;
    	long fileSizeInMB = fileSizeInKB / 1024;
    	return fileSizeInMB;
	}
	
	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	public static String createUniqueFileName(String fileName) {
		 return fileName.substring(0, fileName.lastIndexOf("."))+ "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ getExtension(fileName);
	}
	
	public static String getFileNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
}
