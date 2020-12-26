package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.NoticeDTO;
import it.solving.padelmanagement.dto.message.insert.NoticeInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.NoticeUpdateMessageDTO;
import it.solving.padelmanagement.mapper.NoticeMapper;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Notice;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private ClubRepository clubRepository;
	
	public NoticeDTO findById(Long id) {
		if (noticeRepository.findById(id).isPresent()) {
			return noticeMapper.convertEntityToDTO(noticeRepository.findById(id).get());			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<NoticeDTO> findAll() {
		return noticeMapper.convertEntityToDTO(noticeRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	public void insert(NoticeInsertMessageDTO noticeInsertMessageDTO) {
		Notice notice=noticeMapper.convertInsertMessageDTOToEntity(noticeInsertMessageDTO);
		Club club=clubRepository.findById(Long.parseLong(noticeInsertMessageDTO.getClubId())).orElse(null);
		if(club!=null) {
			notice.setClub(club);
			club.addToNotices(notice);
			noticeRepository.save(notice);
			clubRepository.save(club);
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public void update(NoticeUpdateMessageDTO noticeUpdateMessageDTO) {
		if (noticeRepository.findById(Long.parseLong(noticeUpdateMessageDTO.getId())).isPresent()) {
			Notice notice=noticeMapper.convertUpdateMessageDTOToEntity(noticeUpdateMessageDTO);
			Club club=clubRepository.findById(Long.parseLong(noticeUpdateMessageDTO.getClubId())).orElse(null);
			if(club!=null) {
				notice.setClub(club);
				club.addToNotices(notice);
				noticeRepository.save(notice);
				clubRepository.save(club);
			} else {
				throw new NoSuchElementException();
			}			
		} else {
			throw new NoSuchElementException();
		}
		
	}
	
	public void delete (Long id) {
		if (noticeRepository.findByIdWithClub(id).isPresent()) {
			Notice notice=noticeRepository.findByIdWithClub(id).get();
			Club club=notice.getClub();
			club.removeFromNotices(notice);
			notice.setClub(null);
			clubRepository.save(club);
			noticeRepository.delete(notice);
		}
	}
	
}
