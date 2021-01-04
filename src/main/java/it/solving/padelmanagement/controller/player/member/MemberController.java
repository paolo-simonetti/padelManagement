package it.solving.padelmanagement.controller.player.member;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.NoticeDTO;
import it.solving.padelmanagement.dto.PlayerDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.member.InputUpdateMemberMessageDTO;
import it.solving.padelmanagement.exception.AbandonClubException;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
import it.solving.padelmanagement.service.PlayerService;
import it.solving.padelmanagement.util.MyUtil;
import it.solving.padelmanagement.validator.InputUpdateMemberValidator;

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
	public ResponseEntity<PlayerDTO> getMember() {
		//Recupero il player dal Security context holder
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
		PlayerDTO playerDTO=playerService.findByIdWithClub(player.getId());
		return ResponseEntity.status(HttpStatus.OK).body(playerDTO);
	}
	
	@PutMapping("update")
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
	public ResponseEntity<ResultDTO> abandonClub() throws AbandonClubException {
		playerService.abandonClub();
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The player has successfully abandoned the club"));
	}
	
	@GetMapping("getnotices")
	public ResponseEntity<Set<NoticeDTO>> getClubNotices() {
		return ResponseEntity.status(HttpStatus.OK).body(playerService.getClubNotices());
	}
	
}
