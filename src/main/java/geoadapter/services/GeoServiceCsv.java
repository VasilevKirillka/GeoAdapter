package geoadapter.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import geoadapter.dto.CityDto;
import geoadapter.dto.CountryDto;
import geoadapter.mapper.GeoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeoServiceCsv {

    private final GeoMapper geoMapper;
    @Value("${myapp.csv-path-file}")
    private String pathFile;

    public List<CountryDto> csvGeoLoad() {
        List<CountryDto> countryDtoList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(pathFile)))) {
            List<String[]> areas = reader.readAll().stream().skip(1).collect(Collectors.toList());
            for (String[] area : areas) {
                String country = area[4];
                String city = area[0];

                if (countryDel(country)) {
                    CountryDto existingCountryDto = geoMapper.findCountryDto(countryDtoList, country);
                    if (existingCountryDto == null) {
                        existingCountryDto = geoMapper.createCountryDto(country);
                        countryDtoList.add(existingCountryDto);
                    }
                    CityDto cityDto = geoMapper.createCityDto(city);
                    existingCountryDto.getCities().add(cityDto);
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return countryDtoList;
    }


    private boolean countryDel(String country) {
        return !(country.equals("Ukraine")
                || country.equals("Russia")
                || country.equals("Belarus"));
    }
}
