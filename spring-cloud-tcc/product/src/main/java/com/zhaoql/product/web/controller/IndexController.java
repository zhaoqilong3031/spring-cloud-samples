package com.zhaoql.product.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoql.product.domain.entity.Product;
import com.zhaoql.product.domain.entity.ProuctTcc;
import com.zhaoql.product.service.ProductService;
import com.zhaoql.support.tcc.Participant;

@RestController
@RefreshScope
public class IndexController {
	@Autowired
	private ProductService productService;
	@Value("${spring.application.name}")
	private String applicationName;
	
	
	@ResponseBody
	@RequestMapping(value = { "/trying" },method = RequestMethod.POST)
	public Participant trying(@RequestBody Product product) {
		ProuctTcc tcc = productService.trying(product);
		return new Participant("http://" + applicationName + "/" + tcc.getId(),tcc.getExpire());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void confirm(@PathVariable Integer id) {
		productService.confirm(id);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void cancel(@PathVariable Integer id) {
		productService.cancel(id);
	}

}
