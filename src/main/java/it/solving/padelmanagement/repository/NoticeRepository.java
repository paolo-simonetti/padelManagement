package it.solving.padelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
