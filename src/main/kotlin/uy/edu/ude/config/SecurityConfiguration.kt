package uy.edu.ude.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager

@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

  @Autowired
  private val jdbcTemplate: JdbcTemplate? = null

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return NoOpPasswordEncoder.getInstance()
  }

  @Autowired
  @Throws(Exception::class)
  fun configureGlobal(auth: AuthenticationManagerBuilder) {
    auth.jdbcAuthentication().dataSource(jdbcTemplate!!.dataSource)
        .usersByUsernameQuery(
            "select login, password, enabled from usuario where login=?")
        .authoritiesByUsernameQuery(
            "select u.login, r.nombre from usuario u left join usuario_roles ur "
                + "on ur.usuario_id=u.id left join rol r "
                + "on ur.roles_id=r.id where u.login=?")
        .passwordEncoder(passwordEncoder())
  }

  @Throws(Exception::class)
  override fun configure(httpSecurity: HttpSecurity) {
    val ROLE_ADMIN = "ADMIN"
    val ROLE_USER = "USER"
    httpSecurity.authorizeRequests()
        .mvcMatchers(GET, "/usuario/**")
        .hasAnyRole(ROLE_ADMIN, ROLE_USER)
        .mvcMatchers(POST, "/sala/**")
        .hasRole(ROLE_ADMIN)
        .mvcMatchers(PUT, "/sala/**")
        .hasRole(ROLE_ADMIN)
        .mvcMatchers(PATCH, "/sala/**")
        .hasRole(ROLE_ADMIN)
        .mvcMatchers(DELETE, "/sala/**")
        .hasRole(ROLE_ADMIN)
        .mvcMatchers(GET, "/sala/**")
        .hasAnyRole(ROLE_ADMIN, ROLE_USER)
        .mvcMatchers("/h2-console/**").permitAll()
        .mvcMatchers("/explorer/**").permitAll()
        .mvcMatchers("/actuator/**").permitAll()
        .mvcMatchers("/v3/**").permitAll()
    httpSecurity.httpBasic()
    httpSecurity.csrf().disable()
    httpSecurity.headers().frameOptions().disable()
  }

  @Bean
  public override fun userDetailsService(): JdbcUserDetailsManager {
    val manager = JdbcUserDetailsManager()
    manager.jdbcTemplate = jdbcTemplate
    return manager
  }
}
