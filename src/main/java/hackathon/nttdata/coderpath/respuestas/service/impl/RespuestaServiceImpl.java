package hackathon.nttdata.coderpath.respuestas.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;

import hackathon.nttdata.coderpath.respuestas.repository.RespuestaRepository;
import hackathon.nttdata.coderpath.respuestas.config.ApplicationConfiguration;
import hackathon.nttdata.coderpath.respuestas.document.Respuesta;
import hackathon.nttdata.coderpath.respuestas.service.RespuestaService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class RespuestaServiceImpl implements RespuestaService {
	
	private final RespuestaRepository RespuestaRepository;
	private final ApplicationConfiguration configuration;
	
	@Override
	public Mono<Respuesta> findById(String id) {
		// TODO Auto-generated method stub
		return RespuestaRepository.findById(id);
	}
	
	@Override
	public Flux<Respuesta> findAlls() {
		// TODO Auto-generated method stub
		return RespuestaRepository.findAll();
	}
	
	@Override
	public Map<String, Object> balanceadorTest() {
		// TODO Auto-generated method stub
		Map<String, Object> response = Maps.newHashMap();
		response.put("balanceador", configuration.getBalanceadorTest());
		response.put("Respuesta_asset", findAlls());
		return response;
	}
	
	@Override
	public Mono<Respuesta> saves(Respuesta document) {
		// TODO Auto-generated method stub
		return RespuestaRepository.save(document);
	}
	
	@Override
	public Mono<Void> delete(Respuesta document) {
		// TODO Auto-generated method stub
		return RespuestaRepository.delete(document);
	}
	
}
