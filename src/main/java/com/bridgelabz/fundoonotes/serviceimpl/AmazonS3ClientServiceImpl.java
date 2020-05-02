package com.bridgelabz.fundoonotes.serviceimpl;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.fundoonotes.Exceptions.S3BucketException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.AmazonS3ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


@Component
public class AmazonS3ClientServiceImpl implements AmazonS3ClientService {


   private String awsS3AudioBucket;
   private AmazonS3 amazonS3;
   private AmazonS3Client s3;
   private String awsKeyId;
   private Region region;
  
   
 //  private static final Logger logger = LoggerFactory.getLogger(AmazonS3ClientServiceImpl.class);

   @Autowired
   public AmazonS3ClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket,String awsKeyId)
   {
       this.amazonS3 = AmazonS3ClientBuilder.standard()
      .withCredentials(awsCredentialsProvider)
      .withRegion(awsRegion.getName()).build();
       this.awsS3AudioBucket = awsS3AudioBucket;
       this.awsKeyId=awsKeyId;
       this.region=awsRegion;
      
   }
   
   @Async
   public String uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess) throws S3BucketException
   {
       String fileName = multipartFile.getOriginalFilename();
       try {
           //creating the file in the server (temporarily)
           File file = new File(fileName);
           FileOutputStream fos = new FileOutputStream(file);
           fos.write(multipartFile.getBytes());
           fos.close();

           PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);
           if (enablePublicReadAccess) 
           {
               putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);

           }

           this.amazonS3.putObject(putObjectRequest);
           System.out.println("Bef");
           String url="https://"+this.awsS3AudioBucket+".s3."+region+".amazonaws.com/"+fileName;
           file.delete();
           return url;
       } 
       catch ( Exception ex) {
    	   throw new S3BucketException("Image already exists with same name",HttpStatus.NOT_ACCEPTABLE);
       }
   }
   
   @Async
   public String getFileFromS3Bucket(String fileName) {
        String val;
	   if(fileName!=null)
	   {
           val="https://"+this.awsS3AudioBucket+".s3."+region+".amazonaws.com/"+fileName;
 
	   }
	   else
	   {
		   val=null;
	   }
   	return val;
   }
   
   @Async
   public void deleteFileFromS3Bucket(String fileName) throws S3BucketException
   {
	  
       try {
           amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
       } catch (Exception ex) 
       {
    	   throw new S3BucketException("Image not found",HttpStatus.NOT_FOUND);
       }
   }


}
