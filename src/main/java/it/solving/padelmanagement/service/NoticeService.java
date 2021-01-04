package it.solving.padelmanagement.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import it.solving.padelmanagement.dto.NoticeDTO;
import it.solving.padelmanagement.dto.message.insert.NoticeInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.NoticeUpdateMessageDTO;
import it.solving.padelmanagement.exception.ForbiddenOperationException;
import it.solving.padelmanagement.mapper.NoticeMapper;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Notice;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.NoticeRepository;
import it.solving.padelmanagement.securitymodel.AdminPrincipal;

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
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		// Recupero il club dalle info sull'admin
		Club club=clubRepository.findByAdmin(admin).orElseThrow(NoSuchElementException::new);
		// Salvo il messaggio nel contesto di persistenza
		noticeRepository.save(notice);
		// Lo lego al club
		notice.setClub(club);
		notice.setCreationDate(LocalDate.now());
		club.addToNotices(notice);
		noticeRepository.save(notice);
		clubRepository.save(club);
		
	}
	
	public void update(NoticeUpdateMessageDTO noticeUpdateMessageDTO) throws ForbiddenOperationException {
		Notice oldNotice=noticeRepository.findByIdWithClub(Long.parseLong(noticeUpdateMessageDTO.getId()))
			.orElseThrow(NoSuchElementException::new);
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		Club club=oldNotice.getClub();
		if(club.getAdmin()!=admin) {
			throw new ForbiddenOperationException("This club is not managed by the user");
		}
		Notice newNotice=noticeMapper.convertUpdateMessageDTOToEntity(noticeUpdateMessageDTO);
		newNotice.setClub(club);
		newNotice.setCreationDate(oldNotice.getCreationDate());
		club.removeFromNotices(oldNotice);
		club.addToNotices(newNotice);
		noticeRepository.save(newNotice);
		clubRepository.save(club);
					
	}
	
	public void delete (@RequestParam Long noticeId) throws ForbiddenOperationException {
		Notice notice=noticeRepository.findByIdWithClub(noticeId).orElseThrow(NoSuchElementException::new);
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		Club club=notice.getClub();
		if(club.getAdmin()!=admin) {
			throw new ForbiddenOperationException("This club is not managed by the user");
		}
		club.removeFromNotices(notice);
		notice.setClub(null);
		clubRepository.save(club);
		noticeRepository.delete(notice);
	}
	
}
