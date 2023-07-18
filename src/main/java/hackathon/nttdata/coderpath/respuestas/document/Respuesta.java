package hackathon.nttdata.coderpath.respuestas.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "respuesta")
public class Respuesta {
	
	@Id
	private String id;
	
	private String texto;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

}
