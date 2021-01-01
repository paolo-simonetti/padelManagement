package it.solving.padelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
