package ar.edu.utn.dds.k3003.model.eventos.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FallaHeladeraDTO {
  private Long heladeraId;
  private LocalDateTime fecha;
}
