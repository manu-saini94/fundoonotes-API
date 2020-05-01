package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.Exceptions.S3BucketException;

public interface AmazonS3ClientService {
	  String uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess) throws S3BucketException;

	   void deleteFileFromS3Bucket(String fileName) throws S3BucketException;

	String getFileFromS3Bucket(String fileName);
}
