package com.zhaoql.tcc.coordinator.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.zhaoql.core.exception.TccCoordinatorException;
import com.zhaoql.support.tcc.Participant;
import com.zhaoql.tcc.coordinator.domain.TccCoordinatorRequest;

import lombok.extern.log4j.Log4j;

/**
 * @author Zhao Junjian
 */
@Component
@Log4j
public class CoordinatorManager {

	@Autowired
	private RestTemplate restTemplate;
	private static final HttpEntity<?> REQUEST_ENTITY;
	static int count = 0;
	static {
		final HttpHeaders header = new HttpHeaders();
		header.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON_UTF8));
		header.setContentType(MediaType.APPLICATION_JSON_UTF8);
		REQUEST_ENTITY = new HttpEntity<>(header);
	}

	@Retryable(value = { TccCoordinatorException.class }, maxAttempts = 5, backoff = @Backoff(value = 0))
	public void retryConfirm(Participant participant) {
		final ResponseEntity<String> response = restTemplate.exchange(participant.getUri(), HttpMethod.PUT,
				REQUEST_ENTITY, String.class);
		count++;
		if (count < 4 || response.getStatusCode() != HttpStatus.NO_CONTENT) {
			throw new TccCoordinatorException("事务处理失败");
		}
		count = 0;
	}

	@Recover
	public void recover(TccCoordinatorException e) {
		System.out.println(e.getMessage());
	}

	public void confirm(TccCoordinatorRequest request) {
		Preconditions.checkNotNull(request);
		List<Participant> participants = request.getParticipants();
		Preconditions.checkNotNull(participants);
		try {
			participants.forEach(participant -> retryConfirm(participant));
		} catch (Exception e) {
			log.error("调用失败,需人工参与", e);
		}
	}

	public void cancel(TccCoordinatorRequest request) {
		Preconditions.checkNotNull(request);
		List<Participant> participants = request.getParticipants();
		for (Participant participant : participants) {
			try {
				restTemplate.exchange(participant.getUri(), HttpMethod.DELETE, REQUEST_ENTITY, String.class);
			} catch (Exception e) {
				log.warn("撤销失败", e);
			}
		}
	}

}
