package geoadapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class CountryDto {
    private String title;
    private List<CityDto> cities;
}
