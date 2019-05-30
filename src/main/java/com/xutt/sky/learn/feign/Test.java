package com.xutt.sky.learn.feign;

import java.util.List;

import feign.Feign;

public class Test {
	public static void main(String... args) {
		  GitHub github = Feign.builder()
//		                       .decoder(new GsonDecoder())
		                       .target(GitHub.class, "http://127.0.0.1:8080");

		  // Fetch and print a list of the contributors to this library.
		  github.contributors("netflix", "feign");
		  /*for (Contributor contributor : contributors) {
		    System.out.println(contributor.login + " (" + contributor.contributions + ")");
		  }*/
		}
}
