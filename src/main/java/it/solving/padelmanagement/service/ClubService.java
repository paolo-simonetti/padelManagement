package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.ClubDTO;
import it.solving.padelmanagement.dto.message.update.ClubUpdateMessageDTO;
import it.solving.padelmanagement.mapper.ClubMapper;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.Notice;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.AdminRepository;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.CourtRepository;
import it.solving.padelmanagement.repository.JoinProposalRepository;
import it.solving.padelmanagement.repository.NoticeRepository;
import it.solving.padelmanagement.repository.PlayerRepository;

@Service
public class ClubService {

	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
	private CourtService courtService;
	
	@Autowired
	private CourtRepository courtRepository;
	
	@Autowired
	private ClubMapper clubMapper;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private JoinProposalRepository joinProposalRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	public void update (ClubUpdateMessageDTO clubUpdateMessageDTO) throws NoSuchElementException {
		if(clubRepository.findById(Long.parseLong(clubUpdateMessageDTO.getId())).isPresent()) {
			// genero il club aggiornato
			Club club=clubMapper.convertUpdateMessageDTOToEntity(clubUpdateMessageDTO);
			
			// Metto l'informazione sui campi
			if (clubUpdateMessageDTO.getCourtsIds()!=null && clubUpdateMessageDTO.getCourtsIds().size()>0) {
				Set<Court> courts=clubUpdateMessageDTO.getCourtsIds().stream().map(stringId -> 
					courtService.findById(Long.parseLong(stringId))).collect(Collectors.toSet());
				club.setCourts(courts);
				courts.stream().forEach(court->{
					court.setClub(club);
					courtRepository.save(court);
				});
			}
			
			// Metto quella sull'admin
			Admin admin=adminRepository.findById(Long.parseLong(clubUpdateMessageDTO.getAdminId())).get();
			club.setAdmin(admin);
			
			// Quella sui messaggi dell'admin
			if (clubUpdateMessageDTO.getNoticesIds()!=null && clubUpdateMessageDTO.getNoticesIds().size()>0) {
				Set<Notice> notices=clubUpdateMessageDTO.getNoticesIds().stream().map(stringId -> 
					noticeRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
				club.setNotices(notices);
				notices.stream().forEach(notice->{
					notice.setClub(club);
					noticeRepository.save(notice);
				});
			} 
			
			// Quella sulle richieste di adesione
			if (clubUpdateMessageDTO.getJoinProposalsIds()!=null && clubUpdateMessageDTO.getJoinProposalsIds().size()>0) {
				Set<JoinProposal> joinProposals=clubUpdateMessageDTO.getJoinProposalsIds().stream().map(stringId -> 
					joinProposalRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
				club.setJoinProposals(joinProposals);
				joinProposals.stream().forEach(joinProposal->{
					joinProposal.setClub(club);
					joinProposalRepository.save(joinProposal);
				});
			}

			// Quella sui giocatori
			
			if (clubUpdateMessageDTO.getPlayersIds()!=null && clubUpdateMessageDTO.getPlayersIds().size()>0) {
				Set<Player> players=clubUpdateMessageDTO.getPlayersIds().stream().map(stringId -> 
					playerRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
				club.setPlayers(players);
				players.stream().forEach(player->{
					player.setClub(club);
					playerRepository.save(player);
				});
			}
			
			// Infine, aggiorno il club
			clubRepository.save(club);			
	
		} else {
			throw new NoSuchElementException("Club not found!");
		}
	}
	
	public void delete (Long id) throws ConstraintViolationException, NoSuchElementException {
		Club club=clubRepository.findByIdWithAdmin(id).get();
		if (club==null) {
			throw new NoSuchElementException("Club not found!");
		}
		
		if (club.getAdmin()!=null) {
			throw new ConstraintViolationException("A club can't be removed until there's its admin", null, null);
		} 
		
		clubRepository.delete(club);
	}
	
	public ClubDTO findById(Long id) {
		if(clubRepository.findById(id).isPresent()) {
			return clubMapper.convertEntityToDTO(clubRepository.findById(id).get());			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<ClubDTO> findAll() {
		return clubMapper.convertEntityToDTO(clubRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
}
