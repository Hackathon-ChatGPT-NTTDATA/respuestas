package hackathon.nttdata.coderpath.respuestas.controller;

import java.util.Date;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hackathon.nttdata.coderpath.respuestas.service.impl.KafkaProducer;
import hackathon.nttdata.coderpath.respuestas.document.Respuesta;
import hackathon.nttdata.coderpath.respuestas.service.RespuestaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@Slf4j
@RequiredArgsConstructor
public class RespuestaController {

	private final RespuestaService service;

	private final KafkaProducer producer;

	@PostMapping("/producer/{topic}")
	public ResponseEntity<Mono<?>> publishMessage(@PathVariable String topic, @Valid @RequestBody String message) {
		Mono.just(producer).doOnNext(t -> {
			t.publishMessage(topic, message);
		}).onErrorReturn(producer).onErrorResume(e -> Mono.just(producer))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> log.info(x.toString()));
		Mono<String> pAsset = Mono.just(message);

		if (pAsset != null) {
			return new ResponseEntity<>(pAsset, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(Mono.just(new Respuesta()), HttpStatus.I_AM_A_TEAPOT);
	}

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		return ResponseEntity.ok(service.balanceadorTest());
	}

	@GetMapping("/all")
	public Flux<Respuesta> searchAll() {
		Flux<Respuesta> per = service.findAlls();
		log.info("Respuesta ASSET registered: " + per);
		return per;
	}

	@GetMapping("/id/{id}")
	public Mono<Respuesta> searchById(@PathVariable String id) {
		log.info("Respuesta Asset id: " + service.findById(id) + " con codigo: " + id);
		return service.findById(id);
	}

	@PostMapping("/create-respuesta")
	public Mono<Respuesta> createRespuesta(@Valid @RequestBody Respuesta respuestaAsset) {
		log.info("Respuestas hackathon NTTTDATA create: " + service.saves(respuestaAsset));
		Mono.just(respuestaAsset).doOnNext(t -> {

			t.setCreateAt(new Date());

		}).onErrorReturn(respuestaAsset).onErrorResume(e -> Mono.just(respuestaAsset))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> log.info(x.toString()));

		Mono<Respuesta> newPersonalAsset = service.saves(respuestaAsset);

		return newPersonalAsset;
	}

	@PutMapping("/update-respuesta/{id}")
	public ResponseEntity<Mono<?>> updateRespuestaAsset(@PathVariable String id,
			@Valid @RequestBody Respuesta respuestaAsset) {
		Mono.just(respuestaAsset).doOnNext(t -> {
			respuestaAsset.setId(id);
			t.setCreateAt(new Date());

		}).onErrorReturn(respuestaAsset).onErrorResume(e -> Mono.just(respuestaAsset))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> log.info(x.toString()));

		Mono<Respuesta> pAsset = service.saves(respuestaAsset);

		if (pAsset != null) {
			return new ResponseEntity<>(pAsset, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(Mono.just(new Respuesta()), HttpStatus.I_AM_A_TEAPOT);
	}

	
	@DeleteMapping("/delete-respuesta/{id}")
	public ResponseEntity<Mono<Void>> deleteRespuestaAsset(@PathVariable String id) {
		Respuesta respuestaAsset = new Respuesta();
		respuestaAsset.setId(id);
		Mono<Respuesta> newPersonalAsset = service.findById(id);
		newPersonalAsset.subscribe();
		Mono<Void> test = service.delete(respuestaAsset);
		test.subscribe();
		return ResponseEntity.noContent().build();
	}

}
