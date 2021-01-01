package it.solving.padelmanagement.converter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MultipartFileImplForUser implements MultipartFile {
	
	@JsonIgnore
	private long imageSize;
	
	private byte[] image=new byte[100000];
	
	public byte[] getImage() {
		return image;
	}

	public MultipartFileImplForUser() {
		super();
	}
	
	public MultipartFileImplForUser(byte[] image) throws IOException {
		this.setImage(image);
	}
	
	public long getChangedBytes() {
		return imageSize;
	}

	public void setChangedBytes(long imageSize) {
		this.imageSize = imageSize;
	}

	
	public void setImage(byte[] image) throws IOException {
		this.writeImage(image);
	}


	public void writeImage(byte[] input) throws IOException {
		if (input!=null && input.length>0 && input.length<=this.image.length) {
			for (int i=0; i<input.length;i++) {
				image[i]=input[i];
			}
			imageSize=input.length;
		} else if(input.length>this.image.length) {
			throw new IOException("Image too large!");
		}
	}
	
	@Override
	@JsonIgnore
	public String getName() {
		return "proPic";
	}

	@Override
	@JsonIgnore
	public String getOriginalFilename() {
		return "stringaDaCambiare";
	}

	@Override
	public String getContentType() {
		return "image";
	}

	@Override
	@JsonIgnore
	public boolean isEmpty() {
		return imageSize==0L;
	}

	@Override
	@JsonIgnore
	public long getSize() {
		return image.length;
	}

	@Override
	@JsonIgnore
	public byte[] getBytes() throws IOException {
		return image;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(image);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		try (FileOutputStream fos = new FileOutputStream(dest.getAbsolutePath())) {
			   fos.write(this.image);
		}
	}

}
