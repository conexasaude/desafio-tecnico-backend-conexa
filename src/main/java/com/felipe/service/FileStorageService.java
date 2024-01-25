package com.felipe.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.felipe.config.FileStorageConfig;
import com.felipe.exceptions.FileStorageException;
import com.felipe.exceptions.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		Path path = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		
		this.fileStorageLocation = path;
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the upload files will be stored!");
		}
	}
	
	public String storeFile(MultipartFile file, Boolean duplicated) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (originalFilename.contains("..")) {
				throw new FileStorageException(
						"Sorry! Filename contains invalid path sequence " + originalFilename);
			}
			Path targetLocation = this.fileStorageLocation.resolve(originalFilename);
			if (duplicated) {
				targetLocation = verifyExistDuplicate(targetLocation, originalFilename);
			} 
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return targetLocation.getFileName().toString();
		} catch (Exception e) {
			throw new FileStorageException(
					"Could not storage file " + originalFilename + ". Please try again!", e);
		}
	}
	
	public Resource loadFileResource(String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) return resource;
			else throw new MyFileNotFoundException("File not found");
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found" + filename, e);
		}
	}
	
	private Path verifyExistDuplicate(Path targetLocation, String filename) {
        if (Files.exists(targetLocation)) {
			String fileNameWithoutExtension = StringUtils.stripFilenameExtension(filename);
	        String fileExtension = StringUtils.getFilenameExtension(filename);
            int counter = 1;
            do {
                String newFileName = fileNameWithoutExtension + "(" + counter + ")." + fileExtension;
                targetLocation = this.fileStorageLocation.resolve(newFileName);
                counter++;
            } while (Files.exists(targetLocation));
        }
		return targetLocation;
	}
	
}
