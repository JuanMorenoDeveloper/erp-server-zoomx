package uy.edu.ude.entity

import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.*

@Entity
@DynamicUpdate
class Sala {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0

  @NotBlank
  @NotNull
  @Size(max = 256)
  var nombre: String? = ""

  @Size(max = 256)
  @NotNull
  @NotBlank
  var responsable: String? = ""

  @NotNull
  @FutureOrPresent
  var fechaDeReserva: LocalDateTime? = LocalDateTime.now()

  @NotNull
  @Positive
  var tiempoReservaEnHoras: Int? = 0

  @NotNull
  @Size(max = 256)
  @Pattern(regexp = "^(https://cdn.pixabay.com/).*")
  var icono: String? = ""

  constructor() {}

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other !is Sala) {
      return false
    }
    val sala = other as Sala?
    return id == sala!!.id
  }

  override fun hashCode(): Int {
    return 31
  }
}
