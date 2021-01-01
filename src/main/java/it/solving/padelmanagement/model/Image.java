package it.solving.padelmanagement.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Lob
	private MultipartFile image;
	
	private Long size;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) throws IOException {
		this.image = image;
		this.size=Long.valueOf(image.getBytes().length);
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Image(Long id, String name, MultipartFile image, Long size) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.size = size;
	}

	public Image() {
		super();
	}
	
	
	
}
