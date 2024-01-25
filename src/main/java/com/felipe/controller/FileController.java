package com.felipe.controller;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.felipe.model.dto.v1.UploadFileResponseDTO;
import com.felipe.service.FileStorageService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/v1/file")
public class FileController {
	private Logger logger = Logger.getLogger(FileController.class.getName());

	@Autowired
	private FileStorageService service;

	@PostMapping("/uploadFile")
	public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "duplicated", defaultValue = "true") boolean duplicated) {
		logger.info("Storing file to disk");
		var fileName = service.storeFile(file, true);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/file/downloadFile/")
				.path(fileName).toUriString();

		return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
			@RequestParam(value = "duplicates", defaultValue = "true") boolean duplicates) {
		logger.info("Storing files to disk");
		return Arrays.asList(files).stream().map(file -> uploadFile(file, duplicates)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(
			@PathVariable String filename,
			HttpServletRequest request) {
		
		logger.info("Reading a file to disk");
		
		Resource resource = service.loadFileResource(filename);
		String contentType = "";
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			logger.info("Could not determine file type!");
		}
		
		if (contentType.isBlank()) contentType = "application/octet-stream";
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
				
	}
}
