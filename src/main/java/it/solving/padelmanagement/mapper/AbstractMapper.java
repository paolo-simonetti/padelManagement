package it.solving.padelmanagement.mapper;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractMapper<Entity, DTO, InsertMessageDTO, UpdateMessageDTO> {
	
	public abstract DTO convertEntityToDTO(Entity entity);
	
	public abstract Entity convertDTOToEntity(DTO dto);
	
	public abstract Entity convertInsertMessageDTOToEntity(InsertMessageDTO insertMessageDTO);
	
	public abstract Entity convertUpdateMessageDTOToEntity(UpdateMessageDTO updateMessageDTO);
	
	public Set<DTO> convertEntityToDTO(Set<Entity> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toSet());
        
    }

    public Set<Entity> convertDTOToEntity(Set<DTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream().map(this::convertDTOToEntity).collect(Collectors.toSet());
    }

	
}
