package com.example.restdemo;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RestdemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private RestdemoController restdemoController;

	@Test
	void contextLoads() {
	}

	@Test
	public void testUploadFileWithSuccess() throws Exception {


		String fileName = "testFileUpload.txt";
		MockMultipartFile file = new MockMultipartFile(fileName, "testFileUpload.txt",
				"text/plain", fileName.getBytes());

		ResponseEntity responseEntity = new ResponseEntity(OK);
		responseEntity.getStatusCode();
		responseEntity.getStatusCodeValue();

		when(restdemoController.uploadFile(fileName, file)).thenReturn(responseEntity);

		this.mockMvc.perform(multipart("/").file(file))
				.andExpect(status().isFound());

	}

	@Test
	public void testUploadFileWithoutSuccess() throws Exception {

		String fileName = "testFileNotUploaded.txt";
		MockMultipartFile file = new MockMultipartFile(fileName, "testFileNotUploaded.txt",
				"text/plain", fileName.getBytes());

		ResponseEntity responseEntity = new ResponseEntity(OK);
		responseEntity.getStatusCode();
		responseEntity.getStatusCodeValue();

		when(restdemoController.uploadFile(fileName, file)).thenReturn(responseEntity);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/fileName").accept(MediaType.TEXT_PLAIN);

		this.mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
	}

	@Test
	public void testDownloadFileWithResource() throws Exception {

		String fileName = "testDownloadFile.txt";
		Path path = Paths.get(fileName);
		UrlResource resource = new UrlResource(path.toUri());
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/fileName").accept(MediaType.TEXT_PLAIN);

		this.mockMvc.perform(requestBuilder).andExpect(status().isFound());

	}

	@Test
	public void testDownloadFileWithoutResource() throws Exception{
		String fileName = "testDownloadFile.txt";
		Path path = Paths.get(fileName);
		UrlResource resource = new UrlResource(path.toUri());
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/fileName").accept(MediaType.TEXT_PLAIN);

		this.mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
	}

}
