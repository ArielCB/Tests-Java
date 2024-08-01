package es.codeurjc.ais.nitflex.utils;

import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class URLTest{
	
	private String url;
	private UrlUtils urlUtils;
	
	@BeforeEach
	void setUp() {
		//Asignacion del objeto que vamos a testear
		urlUtils = new UrlUtils();
	}
	
	@Test
	@DisplayName("URL valida que no es una imagen")
	void test() {
		//Asignamos a url una url valida que existe pero no es una imagen
		url = "https://www.themoviedb.org/";
		
		//Comprobar el lanzamiento del error
		ResponseStatusException ex = assertThrows(ResponseStatusException.class, ()->urlUtils.checkValidImageURL(url));
		assertEquals("The url is not an image resource",ex.getReason());
	}
	
	@Test
	@DisplayName("URL valida")
	void test1() {
		//Esta vez asiganamos a url una url correcta
		url = "https://image.tmdb.org/t/p/w220_and_h330_face/m6XWQpT0biTpe5wBGWd60RXmtEX.jpg";
		
		//Comprobar que no salta ningun error ya que es valido
		assertDoesNotThrow(()->urlUtils.checkValidImageURL(url));
	}
}