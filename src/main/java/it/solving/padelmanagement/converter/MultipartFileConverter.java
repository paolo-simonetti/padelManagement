package it.solving.padelmanagement.converter;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.web.multipart.MultipartFile;

@Converter(autoApply=true)
public class MultipartFileConverter implements AttributeConverter<MultipartFile, Blob> {

	@Override
	public Blob convertToDatabaseColumn(MultipartFile attribute) {
		try {
			return new SerialBlob(attribute.getBytes());
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public MultipartFile convertToEntityAttribute(Blob dbData) {
		try {
			return new MultipartFileImplForUser(dbData.getBytes(1L,(int) dbData.length()));
		} catch (SQLException e) {
			e.printStackTrace();
			return new MultipartFileImplForUser();
		}
	}

}
