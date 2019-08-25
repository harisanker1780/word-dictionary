package com.wd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileProperties {
	
	private String storageDir;
	
	private String allowedExtensions;
	
	private long maxSizeInMB;

	public String getStorageDir() {
		return storageDir;
	}

	public void setStorageDir(String storageDir) {
		this.storageDir = storageDir;
	}

	public String[] getAllowedExtensions() {
		return allowedExtensions != null ? allowedExtensions.split(",") : new String[0];
	}

	public void setAllowedExtensions(String allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}

	public long getMaxSizeInMB() {
		return maxSizeInMB;
	}

	public void setMaxSizeInMB(long maxSizeInMB) {
		this.maxSizeInMB = maxSizeInMB;
	}
}
