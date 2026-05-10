package cars.microservice.CarsMicroservice.export;

import cars.microservice.CarsMicroservice.dtos.AdExportDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("csvExport")
public class CsvExportStrategy extends AdExportTemplate {

    @Override
    protected String writeOutput(List<AdExportDTO> dtos) {
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Brand,Model,Chassis,Year,Price,Seller\n");

        for (AdExportDTO dto : dtos) {
            csv.append(dto.getId()).append(",")
                    .append(escapeCsv(dto.getBrand())).append(",")
                    .append(escapeCsv(dto.getModel())).append(",")
                    .append(escapeCsv(dto.getChassis())).append(",")
                    .append(dto.getYear()).append(",")
                    .append(dto.getPrice()).append(",")
                    .append(escapeCsv(dto.getSeller())).append("\n");
        }
        return csv.toString();
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    @Override
    public String getContentType() { return "text/csv"; }

    @Override
    public String getFileExtension() { return ".csv"; }
}