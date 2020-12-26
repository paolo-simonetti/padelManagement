package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.AdminDTO;
import it.solving.padelmanagement.dto.message.insert.ClubInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.UserUpdateMessageDTO;
import it.solving.padelmanagement.mapper.AdminMapper;
import it.solving.padelmanagement.mapper.ClubMapper;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.repository.AdminRepository;
import it.solving.padelmanagement.repository.ClubRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired 
	private AdminMapper adminMapper;
		
	@Autowired
	private ClubMapper clubMapper;
	
	@Autowired
	private UserService userService;
	
	public void insert (UserInsertMessageDTO adminInsertMessageDTO, ClubInsertMessageDTO clubInsertMessageDTO) {
		Admin admin=adminMapper.convertInsertMessageDTOToEntity(adminInsertMessageDTO);
		Club club=clubMapper.convertInsertMessageDTOToEntity(clubInsertMessageDTO);		
		// Rimuovo l'admin dalla tabella User
		userService.delete(admin.getId());
		// Imposto il club all'admin e salvo tutto nel contesto di persistenza
		admin.setClub(club);
		club.setAdmin(admin);
		clubRepository.save(club);
		adminRepository.save(admin);
	}
	
	public void update (UserUpdateMessageDTO adminUpdateMessageDTO) throws NoSuchElementException {
		// L'aggiornamento non coinvolge il club amministrato, i.e. non può essere assegnato a un club diverso
		if(adminRepository.findById(Long.parseLong(adminUpdateMessageDTO.getId())).isPresent()) {
			Admin admin= adminMapper.convertUpdateMessageDTOToEntity(adminUpdateMessageDTO);
			adminRepository.save(admin);			
		} else {
			throw new NoSuchElementException("Admin not found!");
		}
	}
	
	public void delete (Long idAdmin) throws NoSuchElementException {
		/* Considero la possibilità di cancellare l'admin solo come via del superAdmin di correggere 
			un proprio errore. In questo caso, elimino anche il suo club.
			Se si vuole tenere il club, ed eliminare l'admin, si deve adottare un metodo del controller apposito. 
			TODO: scrivi un metodo in ClubController per fare questa cosa */
		if(adminRepository.findById(idAdmin).isPresent()) {
			adminRepository.delete(adminRepository.findById(idAdmin).get());
		} else {
			throw new NoSuchElementException("Admin not found!");
		}
	}
	
	public AdminDTO findById (Long idAdmin) {
		if (adminRepository.findById(idAdmin).isPresent()) {
			return adminMapper.convertEntityToDTO(adminRepository.findById(idAdmin).get());			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<AdminDTO> findAll() {
		return adminMapper.convertEntityToDTO(adminRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	
}
