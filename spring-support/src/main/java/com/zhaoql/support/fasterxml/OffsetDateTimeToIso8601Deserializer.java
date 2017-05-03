package com.zhaoql.support.fasterxml;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class OffsetDateTimeToIso8601Deserializer extends JsonDeserializer<OffsetDateTime> {

	@Override
	public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return OffsetDateTime.parse(p.getText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
}
