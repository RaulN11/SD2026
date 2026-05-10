package cars.microservice.CarsMicroservice.export;

import cars.microservice.CarsMicroservice.dtos.AdExportDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("jsonExport")
public class JsonExportStrategy extends AdExportTemplate {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected String writeOutput(List<AdExportDTO> dtos) throws Exception {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dtos);
    }

    @Override
    public String getContentType() { return "application/json"; }

    @Override
    public String getFileExtension() { return ".json"; }
}