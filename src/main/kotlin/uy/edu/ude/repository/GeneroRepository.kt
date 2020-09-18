package uy.edu.ude.repository

import java.util.Optional
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import uy.edu.ude.entity.Genero

@RepositoryRestResource(collectionResourceRel = "genero", path = "genero")
interface GeneroRepository : CrudRepository<Genero, Long> {

  fun findByNombre(@Param("nombre") nombre: String): Optional<Genero>
}
