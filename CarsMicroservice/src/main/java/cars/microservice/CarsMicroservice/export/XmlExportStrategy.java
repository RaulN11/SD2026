package cars.microservice.CarsMicroservice.export;

import cars.microservice.CarsMicroservice.dtos.AdExportDTO;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("xmlExport")
public class XmlExportStrategy extends AdExportTemplate {

    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    protected String writeOutput(List<AdExportDTO> dtos) throws Exception {
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dtos);
    }

    @Override
    public String getContentType() { return "application/xml"; }

    @Override
    public String getFileExtension() { return ".xml"; }
}