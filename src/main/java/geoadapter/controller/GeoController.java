package geoadapter.controller;

import geoadapter.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import geoadapter.services.GeoServiceCsv;
import geoadapter.services.GeoServiceHH;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("msg")
@RequiredArgsConstructor
public class GeoController {
    private final KafkaTemplate<String, CountryDto> countryKafkaTemplate;
    private final GeoServiceHH geoServiceHH;
    private final GeoServiceCsv geoServiceCsv;
    @Value("${spring.kafka.topic}")
    private String geoTopic;

    @Value("${myapp.data-source}")
    private String dataSource;


    @PutMapping("/load")
    public void load() {
        List<CountryDto> countryDtoList = addCountry();
        for (CountryDto countryDto : countryDtoList) {
            countryKafkaTemplate.send(geoTopic, countryDto);
        }
    }

    private List<CountryDto> addCountry() {
        List<CountryDto> countryDtoList = new ArrayList<>();
        if ("hh".equals(dataSource)) {
            countryDtoList.addAll(geoServiceHH.hhGeoLoad());
        } else if ("csv".equals(dataSource)) {
            countryDtoList.addAll(geoServiceCsv.csvGeoLoad());
        } else if ("all".equals(dataSource)) {
            countryDtoList.addAll(geoServiceCsv.csvGeoLoad());
            countryDtoList.addAll(geoServiceHH.hhGeoLoad());
        } else {
            log.info("Выбран неверный ресурс");
        }

        return countryDtoList;
    }
}
