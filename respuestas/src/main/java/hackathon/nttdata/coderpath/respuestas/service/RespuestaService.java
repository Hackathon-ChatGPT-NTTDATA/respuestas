package hackathon.nttdata.coderpath.respuestas.service;

import java.util.Map;

import hackathon.nttdata.coderpath.respuestas.document.Respuesta;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RespuestaService {
	
	Mono<Respuesta> findById(String id);

	Flux<Respuesta> findAlls();

	Mono<Respuesta> saves(Respuesta document);

	Mono<Void> delete(Respuesta document);

	Map<String, Object> balanceadorTest();
	
	

}
