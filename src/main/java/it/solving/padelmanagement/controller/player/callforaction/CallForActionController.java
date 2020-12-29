package it.solving.padelmanagement.controller.player.callforaction;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.MatchDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.callforactions.InputUpdateMissingPlayersMessageDTO;
import it.solving.padelmanagement.service.MatchService;
import it.solving.padelmanagement.util.MyUtil;
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
	private MyUtil myUtil;
	
	@PutMapping("updateMissingPlayers")
	public ResponseEntity<ResultDTO> updateMissingPlayers(@Valid @RequestBody InputUpdateMissingPlayersMessageDTO
			inputMessage, BindingResult bindingResult) {
		inputUpdateMissingPlayersValidator.validate(inputMessage,bindingResult);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(
					myUtil.allErrorsToString(bindingResult)));
		}
		matchService.updateMissingPlayers(inputMessage);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The call-for-action was successfully updated"));
	}
	
	@GetMapping("getOthers") 
	public ResponseEntity<Object> getOthersCallForActions(@RequestParam Long idPlayer) {
		Set<MatchDTO> result=matchService.findAllOthersCallForActions(idPlayer);
		if (result==null || result.size()==0) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("No call-for-actions were found :("));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}
}
