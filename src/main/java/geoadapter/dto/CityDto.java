package geoadapter.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CityDto {
    private String title;
    private UUID countryId;

}
