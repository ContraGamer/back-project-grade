package com.ud.csrf.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
		      //Creating KeyPair generator object
			//   try {
			// 	KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
      
			// 	//Initializing the KeyPairGenerator
			// 	keyPairGen.initialize(2048);
				
			// 	//Generating the pair of keys
			// 	KeyPair pair = keyPairGen.generateKeyPair();
				
			// 	//Getting the private key from the key pair
			// 	PrivateKey privKey = pair.getPrivate(); 
			// 	System.out.println(privKey.getEncoded());  
				
			// 	//Getting the public key from the key pair
			// 	PublicKey publicKey = pair.getPublic(); 
			// 	System.out.println(publicKey.getEncoded());
			// 	System.out.println("Keys generated");
			//   } catch (Exception e) {
			// 	e.printStackTrace();
			//   }

	}

}
