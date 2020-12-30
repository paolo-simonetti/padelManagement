package it.solving.padelmanagement.controller.player.member;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.NoticeDTO;
import it.solving.padelmanagement.dto.PlayerDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.member.InputUpdateMemberMessageDTO;
import it.solving.padelmanagement.exception.AbandonClubException;
import it.solving.padelmanagement.service.PlayerService;
import it.solving.padelmanagement.util.MyUtil;
import it.solving.padelmanagement.validator.InputUpdateMemberValidator;

//TODO: in tutto questo package, devo controllare che il socio stia agendo sulle proprie robe
@RestController
@RequestMapping("player/member")
public class MemberController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private InputUpdateMemberValidator inputUpdateMemberValidator;
	
	@Autowired
	private MyUtil myUtil;
	
	@GetMapping("get")
	public ResponseEntity<PlayerDTO> getMember(@RequestParam Long id) {
		PlayerDTO playerDTO=playerService.findByIdWithClub(id);
		return ResponseEntity.status(HttpStatus.OK).body(playerDTO);
	}
	
	@PostMapping("update")
	public ResponseEntity<ResultDTO> updateMember(@RequestBody InputUpdateMemberMessageDTO inputMessage, 
		BindingResult bindingResult) {
		inputUpdateMemberValidator.validate(inputMessage,bindingResult);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(
					myUtil.allErrorsToString(bindingResult)));
		} 
		playerService.updatePersonalData(inputMessage);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Personal data were successfully updated!"));
	}
	
	@DeleteMapping("abandonclub")
	public ResponseEntity<ResultDTO> abandonClub(@RequestParam Long playerId) throws AbandonClubException {
		// ricordati il controllo sullo stato di pagamento 
		playerService.abandonClub(playerId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The player has successfully abandoned the club"));
	}
	
	@GetMapping("getnotices")
	public ResponseEntity<Set<NoticeDTO>> getClubNotices(@RequestParam Long playerId) {
		return ResponseEntity.status(HttpStatus.OK).body(playerService.getClubNotices(playerId));
	}
}
