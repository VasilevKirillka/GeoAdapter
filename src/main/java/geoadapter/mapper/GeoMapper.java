package geoadapter.mapper;


import geoadapter.dto.CityDto;
import geoadapter.dto.CountryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeoMapper {

    public CountryDto createCountryDto(String country) {

        CountryDto countryDto = new CountryDto();
        countryDto.setTitle(country);
        countryDto.setCities(new ArrayList<>());
            return countryDto;
        }

    public CityDto createCityDto(String city){
        CityDto cityDto = new CityDto();
        cityDto.setTitle(city);
        return cityDto;
    }

    public CountryDto findCountryDto(List<CountryDto> countryDtoList, String country) {
        for (CountryDto countryDto : countryDtoList) {
            if (countryDto.getTitle().equals(country)) {
                return countryDto;
            }
        }
        return null;
    }
}
