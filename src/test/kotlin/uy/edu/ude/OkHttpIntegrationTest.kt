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
  fun givenAuthUser_whenGetGeneros_thenGetResponseOk() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://localhost:8080/genero")
        .header("Authorization", Credentials.basic("user", "user"))
        .build()

    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(200)
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
  fun givenUnAuthUser_whenGetDepartamento_thenGetResponse401() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://localhost:8080/genero")
        .header("Authorization", Credentials.basic("bad", "wrong"))
        .build()

    val response = client.newCall(request).execute()
    assertThat(response.code).isEqualTo(401)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenUsuario_whenPostGenero_thenGetResponse403() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """{"nombre":"Prueba"}"""
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/genero")
        .header("Authorization", Credentials.basic("user", "user"))
        .post(body).build()

    val response = client.newCall(request).execute()
    assertThat(response.code).isEqualTo(403)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdmin_whenPostPelicula_thenGetResponse201() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {"director": "Carlos3000",
      "titulo": "Test 123",
      "fecha_estreno": "2019-12-12",
      "poster": "https://as.com/meristation/imagenes/2019/03/19/noticias/1553025770_364735_1553025920_sumario_normal2.jpg",
      "genero":"http://localhost:8080/genero/2"}""".trimIndent()
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/pelicula")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .post(body).build()
    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(201)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdmin_whenPostPeliculaWithInvalidFecha_thenGetResponse400() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {"director": "Carlos3000",
      "titulo": "Test 123",
      "fechaEstreno": "2019-12-100",
      "poster": "https://as.com/meristation/imagenes/2019/03/19/noticias/1553025770_364735_1553025920_sumario_normal2.jpg",
      "genero":"http://localhost:8080/genero/2"}""".trimIndent()
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/pelicula")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .post(body).build()
    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(400)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdm_whenPostEstudianteWithInvalidDirector_thenGetResponse500() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {"director": null,
      "titulo": "Test 123",
      "fechaEstreno": "2019-12-10",
      "poster": "https://as.com/meristation/imagenes/2019/03/19/noticias/1553025770_364735_1553025920_sumario_normal2.jpg",
      "genero":"http://localhost:8080/genero/2"}""".trimIndent()
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/pelicula")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .post(body).build()
    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(400)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdmin_whenPutPelicula_thenGetResponse204() {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val json = """
      {"director": "null",
      "titulo": "Test 123",
      "fechaEstreno": "2019-12-10",
      "poster": "https://as.com/meristation/imagenes/2019/03/19/noticias/1553025770_364735_1553025920_sumario_normal2.jpg",
      "genero":"http://localhost:8080/genero/2"}""".trimIndent()
    val body = json.toRequestBody(JSON)
    val request = Request.Builder()
        .url("http://localhost:8080/pelicula/1")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .patch(body).build()
    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(204)
    response.close()
  }

  @Test
  @Throws(IOException::class)
  fun givenAdmin_whenDeletePelicula_thenGetResponse204() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://localhost:8080/pelicula/1")
        .header("Authorization", Credentials.basic("adm", "adm"))
        .delete().build()

    val response = client.newCall(request).execute()

    assertThat(response.code).isEqualTo(204)
    response.close()
  }
}
