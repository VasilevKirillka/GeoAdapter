package geoadapter.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import geoadapter.dto.CityDto;
import geoadapter.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import geoadapter.mapper.GeoMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoServiceHH {
    RestTemplate restTemplate = new RestTemplate();
    private final static String PATHURL = "https://api.hh.ru/areas"; // вынеси в аппликейшн
    private final GeoMapper geoMapper;

    public List<CountryDto> hhGeoLoad() {
        List<CountryDto> countryDtoList = new ArrayList<>();

        try {
            String data = restTemplate.getForObject(PATHURL, String.class);
            String[][] areas = parseAreas(data);

            for (String[] area : areas) {
                String country = area[1];
                String city = area[3];

                if (!country.equals("Другие регионы")) {
                    CountryDto existingCountryDto=geoMapper.findCountryDto(countryDtoList, country);
                    if (existingCountryDto == null) {
                        existingCountryDto = geoMapper.createCountryDto(country);
                        countryDtoList.add(existingCountryDto);
                    }
                    CityDto cityDto = geoMapper.createCityDto(city);
                    existingCountryDto.getCities().add(cityDto);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countryDtoList;
    }

    private static String[][] parseAreas(String data) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(data);
        List areas = new ArrayList<>();

        for (JsonNode json : jsonNode) {
            for (JsonNode area : json.get("areas")) {
                if (area.has("areas")) {
                    for (JsonNode innerArea : area.get("areas")) {
                        areas.add(new String[]{json.get("id").asText(), json.get("name").asText(), area.get("id").asText(), innerArea.get("name").asText()});
                    }
                } else {
                    areas.add(new String[]{json.get("id").asText(), json.get("name").asText(), area.get("id").asText(), area.get("name").asText()});
                }
            }
        }

        return (String[][]) areas.toArray(new String[0][0]);
    }

}
