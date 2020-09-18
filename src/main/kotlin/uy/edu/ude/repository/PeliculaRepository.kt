package uy.edu.ude.repository

import java.time.LocalDate
import java.util.Optional
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import uy.edu.ude.entity.Genero
import uy.edu.ude.entity.Pelicula

@RepositoryRestResource(collectionResourceRel = "pelicula", path = "pelicula")
interface PeliculaRepository : CrudRepository<Pelicula, Long> {

  fun findByFechaEstreno(@Param("fechaEstreno") fechaEstreno: LocalDate): Optional<Pelicula>

  fun findByDirectorContains(@Param("director") director: String): Optional<Pelicula>

  fun findByGenero(@Param("genero") genero: Genero): Optional<Pelicula>
}
