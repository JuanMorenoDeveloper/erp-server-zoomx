package uy.edu.ude.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.format.annotation.DateTimeFormat
import uy.edu.ude.entity.Sala
import java.time.LocalDateTime

@RepositoryRestResource(collectionResourceRel = "sala", path = "sala")
interface SalaRepository : CrudRepository<Sala, Long> {

  fun findAllByNombreContains(@Param("nombre") nombre: String): List<Sala>

  fun findAllByResponsableContains(@Param("responsable") nombre: String): List<Sala>

  fun findAllByFechaDeReserva(@Param("fechaDeReserva") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) fechaDeReserva: LocalDateTime): List<Sala>
}
