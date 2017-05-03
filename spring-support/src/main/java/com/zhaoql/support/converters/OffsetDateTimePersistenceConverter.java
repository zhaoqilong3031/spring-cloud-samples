package com.zhaoql.support.converters;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OffsetDateTimePersistenceConverter implements AttributeConverter<OffsetDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(OffsetDateTime attribute) {
		return Timestamp.valueOf(attribute.toLocalDateTime());
	}

	@Override
	public OffsetDateTime convertToEntityAttribute(Timestamp dbData) {
		return OffsetDateTime.of(dbData.toLocalDateTime(), ZoneOffset.UTC);
	}
}