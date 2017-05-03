package com.zhaoql.tcc.coordinator.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoql.tcc.coordinator.manager.CoordinatorManager;
import com.zhaoql.tcc.coordinator.domain.TccCoordinatorRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@RefreshScope
@RequestMapping(produces = "application/tcc+json")
@Slf4j
public class CoordinatorController {
	@Autowired
	CoordinatorManager coordinatorManager;

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/coordinator", method = RequestMethod.PUT)
	public void confirm(@Valid @RequestBody TccCoordinatorRequest request, BindingResult result) {
		coordinatorManager.confirm(request);
	}
 

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/coordinator", method = RequestMethod.DELETE)
	public void cancel(@Valid @RequestBody TccCoordinatorRequest request, BindingResult result) {
		coordinatorManager.cancel(request);
	}
}
