package com.kmakrutin.photo.app.api.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PhotoAppApiUsersApplication
{

  public static void main( String[] args )
  {
    SpringApplication.run( PhotoAppApiUsersApplication.class, args );
  }

}
