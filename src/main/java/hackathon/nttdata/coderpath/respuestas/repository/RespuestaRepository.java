package hackathon.nttdata.coderpath.respuestas.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import hackathon.nttdata.coderpath.respuestas.document.Respuesta;

@Repository
public interface RespuestaRepository extends ReactiveMongoRepository<Respuesta, String> {

}
