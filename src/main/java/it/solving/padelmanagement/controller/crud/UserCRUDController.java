package it.solving.padelmanagement.controller.crud;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.UserDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.service.UserService;
import it.solving.padelmanagement.validator.SignUpValidator;

@RestController
@RequestMapping("crudUser")
public class UserCRUDController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SignUpValidator signUpValidator;
	
	@GetMapping("findById")
	public ResponseEntity<Object> findById(@RequestParam Long id) {
		UserDTO userDTO = userService.findById(id);
		if (userDTO!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(userDTO);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO("User not found!"));
		}
	}
	
	@PostMapping(path="insert",consumes="multipart/form-data;charset=UTF-8")
	public ResponseEntity<ResultDTO> insert(@Valid @ModelAttribute UserInsertMessageDTO userInsertMessageDTO, BindingResult bindingResult) throws IOException {
		signUpValidator.validate(userInsertMessageDTO,bindingResult);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO("User already exists"));
		}
		userService.insert(userInsertMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("New user successfully inserted")); 
	}
	
	
	
}
