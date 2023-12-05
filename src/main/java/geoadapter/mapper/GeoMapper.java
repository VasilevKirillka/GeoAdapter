package geoadapter.mapper;

import geoadapter.dto.CityDto;
import geoadapter.dto.CountryDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface GeoMapper {
    default CountryDto createCountryDto(String country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setTitle(country);
        countryDto.setCities(new ArrayList<>());
        return countryDto;
    }

    default CityDto createCityDto(String city) {
        CityDto cityDto = new CityDto();
        cityDto.setTitle(city);
        return cityDto;
    }

    default CountryDto findCountryDto(List<CountryDto> countryDtoList, String country) {
        for (CountryDto countryDto : countryDtoList) {
            if (countryDto.getTitle().equals(country)) {
                return countryDto;
            }
        }
        return null;
    }
}
