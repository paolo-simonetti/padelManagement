package it.solving.padelmanagement.controller.admin.notices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.insert.NoticeInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.NoticeUpdateMessageDTO;
import it.solving.padelmanagement.service.NoticeService;

//TODO: in tutte questo package, devo controllare che l'admin stia agendo su roba riguardante il proprio circolo
@RestController
@RequestMapping("admin/notices")
public class NoticesController {

	@Autowired
	private NoticeService noticeService;
	
	@PostMapping("insert")
	public ResponseEntity<ResultDTO> insertNotice(@RequestBody NoticeInsertMessageDTO noticeInsertMessageDTO) {
		noticeService.insert(noticeInsertMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The notice was successfully inserted"));
	}
	
	@PutMapping("update")
	public ResponseEntity<ResultDTO> updateNotice(@RequestBody NoticeUpdateMessageDTO noticeUpdateMessageDTO) {
		noticeService.update(noticeUpdateMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The notice was successfully updated"));
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<ResultDTO> deleteNotice(@RequestParam Long noticeId) {
		noticeService.delete(noticeId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The notice was successfully deleted"));
	}
}
