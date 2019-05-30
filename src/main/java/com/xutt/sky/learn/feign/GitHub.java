package com.xutt.sky.learn.feign;

import java.util.List;

import feign.Param;
import feign.RequestLine;

public interface GitHub {
	@RequestLine("GET /sky/service/user/operation/queryById")
	  List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
