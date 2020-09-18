package uy.edu.ude.entity

import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@DynamicUpdate
class Pelicula {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0
  @NotBlank
  @NotNull
  @Size(max = 256)
  var titulo: String? = ""
  @Size(max = 256)
  @NotNull
  @NotBlank
  var director: String? = ""
  @NotNull
  var fechaEstreno: LocalDate? = LocalDate.now()
  @NotNull
  @Size(max = 256)
  @Pattern(regexp = "^(http|https).*")
  var poster: String? = ""

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "genero_id")
  @NotNull
  var genero: Genero = Genero()

  constructor() {}

  constructor(id: Long, @NotNull titulo: String,
              @NotNull director: String,
              @NotNull fechaEstreno: LocalDate, genero: Genero,
              poster: String) {
    this.id = id
    this.titulo = titulo
    this.director = director
    this.fechaEstreno = fechaEstreno
    this.genero = genero
    this.poster = poster
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other !is Pelicula) {
      return false
    }
    val pelicula = other as Pelicula?
    return id == pelicula!!.id
  }

  override fun hashCode(): Int {
    return 31
  }

  override fun toString(): String {
    return "Pelicula{" +
        "id=" + id +
        ", titulo='" + titulo + '\''.toString() +
        ", director='" + director + '\''.toString() +
        ", fechaEstreno='" + fechaEstreno + '\''.toString() +
        ", genero='" + genero + '\''.toString() +
        ", poster='" + poster + '\''.toString() +
        '}'.toString()
  }
}
