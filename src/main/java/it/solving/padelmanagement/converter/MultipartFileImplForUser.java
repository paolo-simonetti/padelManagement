package it.solving.padelmanagement.converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import it.solving.padelmanagement.model.Image;

public class MultipartFileImplForUser implements MultipartFile {

	private Image imageFile;
	
	private long changedBytes;
	
	private byte[] fileSize=new byte[100000];
	
	public MultipartFileImplForUser() {
		super();
	}
	
	public MultipartFileImplForUser(byte[] fileSize) {
		this.setFileSize(fileSize);
	}
	
	public long getChangedBytes() {
		return changedBytes;
	}

	public void setChangedBytes(long changedBytes) {
		this.changedBytes = changedBytes;
	}

	public byte[] getFileSize() {
		return fileSize;
	}

	public void setFileSize(byte[] fileSize) {
		this.writeFileSize(fileSize);
	}

	public Image getImageFile() {
		return imageFile;
	}

	public void setImageFile(Image imageFile) {
		this.imageFile = imageFile;
	}

	public void writeFileSize(byte[] input) {
		if (input!=null && input.length>0 && input.length<this.fileSize.length) {
			for (int i=0; i<input.length;i++) {
				fileSize[i]=input[i];
			}
			changedBytes=input.length;
		} 
	}
	
	@Override
	public String getName() {
		return "proPic";
	}

	@Override
	public String getOriginalFilename() {
		return imageFile.getName();
	}

	@Override
	public String getContentType() {
		return "image";
	}

	@Override
	public boolean isEmpty() {
		return imageFile.getImage()==null;
	}

	@Override
	public long getSize() {
		return fileSize.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return fileSize;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return imageFile.getImage().getInputStream();
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		// TODO Auto-generated method stub

	}

}
