package it.solving.padelmanagement.controller.player.callforaction;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.MatchDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.callforactions.InputCancelParticipationMessageDTO;
import it.solving.padelmanagement.dto.message.callforactions.InputJoinCallForActionMessageDTO;
import it.solving.padelmanagement.dto.message.callforactions.InputUpdateMissingPlayersMessageDTO;
import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.exception.NonAdmissibleActionOnMatchNow;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
import it.solving.padelmanagement.service.MatchService;
import it.solving.padelmanagement.service.PlayerService;
import it.solving.padelmanagement.util.MyUtil;
import it.solving.padelmanagement.validator.InputCancelParticipationValidator;
import it.solving.padelmanagement.validator.InputJoinCallForActionValidator;
import it.solving.padelmanagement.validator.InputUpdateMissingPlayersValidator;

//TODO: in tutto questo package, devo controllare che il player stia agendo sulle proprie robe
@RestController
@RequestMapping("player/callforaction")
public class CallForActionController {
	
	@Autowired
	private InputUpdateMissingPlayersValidator inputUpdateMissingPlayersValidator;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private InputJoinCallForActionValidator inputJoinCallForActionValidator;
	
	@Autowired
	private InputCancelParticipationValidator inputCancelParticipationValidator;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private MyUtil myUtil;
	
	@PutMapping("updateMissingPlayers")
	public ResponseEntity<ResultDTO> updateMissingPlayers(@Valid @RequestBody InputUpdateMissingPlayersMessageDTO
			inputMessage, BindingResult bindingResult) throws EmailException {
		inputUpdateMissingPlayersValidator.validate(inputMessage,bindingResult);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(
					myUtil.allErrorsToString(bindingResult)));
		}
		matchService.updateMissingPlayers(inputMessage);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The call-for-action was successfully updated"));
	}
	
	@GetMapping("getOthers") 
	public ResponseEntity<Object> getOthersCallForActions() {
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
		Set<MatchDTO> result=matchService.findAllOthersCallForActions(player.getId());
		if (result==null || result.size()==0) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("No call-for-actions were found :("));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}
	
	@PostMapping("join")
	public ResponseEntity<ResultDTO> joinInCallForAction(@Valid @RequestBody InputJoinCallForActionMessageDTO 
			inputMessage, BindingResult bindingResult) throws EmailException {
		inputJoinCallForActionValidator.validate(inputMessage,bindingResult);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(
					myUtil.allErrorsToString(bindingResult)));
		}
			
		String message=playerService.joinCallForAction(inputMessage);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(message));
	}
	
	@PostMapping("cancelparticipation")
	public ResponseEntity<ResultDTO> cancelParticipation(@Valid @RequestBody InputCancelParticipationMessageDTO 
			inputMessage, BindingResult bindingResult) throws NonAdmissibleActionOnMatchNow {
		inputCancelParticipationValidator.validate(inputMessage,bindingResult);
		if(bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResultDTO(myUtil.allErrorsToString(bindingResult)));
		}
		playerService.cancelParticipation(inputMessage);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The participation was successfully cancelled."));
	}
}
