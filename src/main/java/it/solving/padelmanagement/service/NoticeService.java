package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.NoticeDTO;
import it.solving.padelmanagement.mapper.NoticeMapper;
import it.solving.padelmanagement.repository.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	public NoticeDTO findById(Long id) {
		if (noticeRepository.findById(id).isPresent()) {
			return noticeMapper.convertEntityToDTO(noticeRepository.findById(id).get());			
		} else {
			throw new NoSuchElementException();
		}
	}
	
}
