package uy.edu.ude

import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.Test
import java.io.IOException

class OkHttpIntegrationTest {

  @Test
  @Throws(IOException::class)
  fun givenAuthUser_whenGetUsuario_thenGetResponseOk() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://localhost:8080/usuario")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .build()
    val rolExpected = "ROLE_ADMIN"

    val response = client.newCall(request).execute()

    assertSoftly {
      it.assertThat(response.code).isEqualTo(200)
      it.assertThat(response.body!!.string()).contains(rolExpected)
    }
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAuthUser_whenGetWrongUrl_thenGetResponse404() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://localhost:8080/generoxyz")
        .header("Authorization", Credentials.basic("usr", "usr"))
        .build()

    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(404)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenUnAuthUser_whenGetSala_thenGetResponse401() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://localhost:8080/sala/1")
        .header("Authorization", Credentials.basic("bad", "wrong"))
        .build()

    val response = client.newCall(request).execute()
    assertThat(response.code).isEqualTo(401)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenUsuario_whenPostSala_thenGetResponse403() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {
        "nombre": "Sala Z",
        "responsable": "Carlos C.",
        "fechaDeReserva": "2020-11-21T11:00:00",
        "tiempoReservaEnHoras": 2,
        "icono": "https://cdn.pixabay.com/photo/2020/09/13/13/00/charles-bridge-5568178_960_720.jpg"
      }
    """
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/sala")
        .header("Authorization", Credentials.basic("user", "user"))
        .post(body).build()

    val response = client.newCall(request).execute()
    assertThat(response.code).isEqualTo(403)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdmin_whenPostSala_thenGetResponse201() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {
        "nombre": "Sala Z",
        "responsable": "Carlos C.",
        "fechaDeReserva": "2020-11-21T11:00:00",
        "tiempoReservaEnHoras": 2,
        "icono": "https://cdn.pixabay.com/photo/2020/09/13/13/00/charles-bridge-5568178_960_720.jpg"
      }
    """
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/sala")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .post(body).build()

    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(201)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdmin_whenSalaPeliculaWithInvalidFecha_thenGetResponse500() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {
        "nombre": "Sala Z",
        "responsable": "Carlos C.",
        "fechaDeReserva": "2019-11-21T11:00:00",
        "tiempoReservaEnHoras": 2,
        "icono": "https://cdn.pixabay.com/photo/2020/09/13/13/00/charles-bridge-5568178_960_720.jpg"
      }
    """
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/sala")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .post(body).build()

    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(500)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdm_whenPostSalaWithInvalidResponsable_thenGetResponse500() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {
        "nombre": "Sala Z",
        "responsable": null,
        "fechaDeReserva": "2019-11-21T11:00:00",
        "tiempoReservaEnHoras": 2,
        "icono": "https://cdn.pixabay.com/photo/2020/09/13/13/00/charles-bridge-5568178_960_720.jpg"
      }
    """
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/sala")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .post(body).build()

    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(500)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdmin_whenPatchSala_thenGetResponse204() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {
        "nombre": "Sala 123",
        "responsable": "Carlos C.",
        "fechaDeReserva": "2020-11-20T10:00:00",
        "tiempoReservaEnHoras": 2,
        "icono": "https://cdn.pixabay.com/photo/2015/05/14/16/02/sandcastle-766949_960_720.jpg"
      }
    """
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/sala/1")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .patch(body).build()

    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(204)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdmin_whenDeleteSala_thenGetResponse204() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://localhost:8080/sala/4")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .delete().build()

    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(204)
    response.close()
  }
}
