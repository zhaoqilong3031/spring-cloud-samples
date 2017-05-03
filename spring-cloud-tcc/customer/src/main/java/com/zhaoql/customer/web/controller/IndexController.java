package com.zhaoql.customer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoql.customer.domain.entity.Customer;
import com.zhaoql.customer.domain.entity.CustomerTcc;
import com.zhaoql.customer.service.CustomerService;
import com.zhaoql.support.tcc.Participant;

@RestController
@RequestMapping
@RefreshScope
public class IndexController {
	@Autowired
	private CustomerService customerService;
	@Value("${spring.application.name}")
	private String applicationName;

	@RequestMapping(value = { "/trying" },method = RequestMethod.POST)
	public Participant trying(@RequestBody Customer customer) {
		CustomerTcc tcc = customerService.trying(customer);
		return new Participant("http://" + applicationName + "/" + tcc.getId(),tcc.getExpire());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void confirm(@PathVariable Integer id) {
		customerService.confirm(id);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void cancel(@PathVariable Integer id) {
		customerService.cancel(id);
	}

}
