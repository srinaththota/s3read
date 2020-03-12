package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@RestController
public class AWSController {

	/*
	 * String connectS3() {
	 * 
	 * AWSCredentials credentials = new BasicAWSCredentials( "<AWS accesskey>",
	 * "<AWS secretkey>" );
	 * 
	 * AmazonS3 s3client = AmazonS3ClientBuilder .standard() .withCredentials(new
	 * AWSStaticCredentialsProvider(credentials)) .withRegion(Regions.US_EAST_2)
	 * .build(); return null; }
	 */
	@RequestMapping("/getkey")
	String getAuthToken(){
		
		String res="";
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
	              .withCredentials(new InstanceProfileCredentialsProvider(true))
	              .build();
		
		S3Object s3object = s3client.getObject("checkiamread", "readdata.txt");
		BufferedReader reader=null;
		try {
		reader = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
		String line="";
		while((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		res=line;
		reader.close();
		}
		catch(Exception e) {
			System.out.println("Got exception");
		}
		
		if(res=="") {
			return "didnt read";
		}else {
		return res;
		}
	}
}
