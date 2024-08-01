package es.codeurjc.ais.nitflex.E2E;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import es.codeurjc.ais.nitflex.Application;

@SpringBootTest(
		classes = Application.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class ModificacionPeliculasTest{
	
	@LocalServerPort
	int port;
	
	private WebDriver driver;
	private String title, url, fecha, sinopsis;
	
	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
		driver.get("http://localhost:"+this.port+"/");
		
		title = "Pulp Fiction";
		fecha = "1995";
		sinopsis = "Pelicula de Quentin Tarantino";
	}
	
	@Test
	@DisplayName("URL incorrecta")
	void testUrlIncorrecta() {
		//Pulsar para crear pelicula
		driver.findElement(By.id("create-film")).click();
		
		//Rellenar datos y enviar
		url = "URL invalida";
		
		driver.findElement(By.name("title")).sendKeys(title);
		driver.findElement(By.name("releaseYear")).sendKeys(fecha);
		driver.findElement(By.name("url")).sendKeys(url);
		driver.findElement(By.name("synopsis")).sendKeys(sinopsis);
		
		driver.findElement(By.id("Save")).click();
		
		//Comprobar el error
		String error;
		String correcto = "The url format is not valid";
		
		error = driver.findElement(By.id("message")).getText();
		assertThat(error).isEqualTo(correcto);
	}
	
	@Test
	@DisplayName("Editar pelicula")
	void testEditarPelicula() {
		//Pulsar para crear pelicula
		driver.findElement(By.id("create-film")).click();
		
		//Rellenar datos y enviar
		url = "https://image.tmdb.org/t/p/w220_and_h330_face/nrSaXF39nDfAAeLKksRCyvSzI2a.jpg";
		
		driver.findElement(By.name("title")).sendKeys(title);
		driver.findElement(By.name("releaseYear")).sendKeys(fecha);
		driver.findElement(By.name("url")).sendKeys(url);
		driver.findElement(By.name("synopsis")).sendKeys(sinopsis);
		
		driver.findElement(By.id("Save")).click();
		
		//Comprobar que se guarde la pelicula con el titulo establecido y editar
		String titulo;
		
		titulo = driver.findElement(By.id("film-title")).getText();
		assertThat(titulo).isEqualTo(title);
		driver.findElement(By.id("edit-film")).click();
		
		//Cambiar el titulo
		String cambio = "- parte 2";
		
		driver.findElement(By.name("title")).sendKeys(cambio);
		driver.findElement(By.id("Save")).click();
		
		//Comprobar el nuevo titulo
		titulo = driver.findElement(By.id("film-title")).getText();
		assertThat(titulo).isEqualTo(title+cambio);
	}
	
}