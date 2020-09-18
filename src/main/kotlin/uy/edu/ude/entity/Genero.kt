package uy.edu.ude.entity

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@DynamicUpdate
class Genero {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null
  @NotNull
  @NotBlank
  @Size(max = 256)
  var nombre: String = ""

  constructor() {}

  constructor(id: Long?, nombre: String) {
    this.id = id
    this.nombre = nombre
  }


  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other !is Genero) {
      return false
    }
    val genero = other as Genero?
    return id != null && id == genero!!.id
  }

  override fun hashCode(): Int {
    return 31
  }

  override fun toString(): String {
    return "Genero{" +
        "id=" + id +
        ", nombre='" + nombre + '\''.toString() +
        '}'.toString()
  }

}
