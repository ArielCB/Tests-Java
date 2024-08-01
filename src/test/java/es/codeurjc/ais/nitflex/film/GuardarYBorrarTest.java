package es.codeurjc.ais.nitflex.film;

import es.codeurjc.ais.nitflex.notification.*;
import es.codeurjc.ais.nitflex.utils.*;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

class GuardarYBorrarTest{
	
	private Film film;
	private FilmService service;
	
	private FilmRepository repository;
	private NotificationService notification;
	private UrlUtils utiles;
	
	@BeforeEach
	void setup() {
		//El setup antes de cada test, donde creamos los objetos que vamos a probar y hacemos mocks de las clases de las que dependen.
		
		repository = mock(FilmRepository.class);
		notification = mock(NotificationService.class);
		utiles = mock(UrlUtils.class);
		
		service = new FilmService(repository, notification, utiles);
	}
	
	@Test
	@DisplayName("Prueba de guardar pelicula sin titulo")
	void test() {
		//Le doy valor a la pelicula con todo correcto pero sin titulo
		String title = "";
		film = new Film(title,"sinopsis",2002,"https://www.themoviedb.org/t/p/w220_and_h330_face/m6XWQpT0biTpe5wBGWd60RXmtEX.jpg");
		
		//Compruebo que se lanza la excepcion que deberia y verifico que la peli no se ha guardado ni notificado
		ResponseStatusException ex = assertThrows(ResponseStatusException.class,()->service.save(film));
		assertEquals("The title is empty", ex.getReason());
		verify(repository,never()).save(film);
		verify(notification,never()).notify(anyString());
	}
	
	@Test
	@DisplayName("Prueba borrar por ID")
	void test1() {
		//Simulo que el repositorio siempre encuentra la pelicula que le pidas por id
		long id = 0;
		when(repository.existsById(anyLong())).thenReturn(true);
		
		//Comprobacion de que no se lanza una excepcion y de que se ha llamado a los metodos deleteById y notify
		assertDoesNotThrow(()->service.delete(id));
		verify(repository,times(1)).deleteById(id);
		verify(notification,times(1)).notify("Film Event: Film with id="+id+" was deleted");
	}
	
}





