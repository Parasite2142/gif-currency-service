package ru.alpha.example.gifcurrencyservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alpha.example.gifcurrencyservice.entites.GiphyObject;
import ru.alpha.example.gifcurrencyservice.entites.enums.WealthStatus;

@FeignClient(name = "giphyApiClient", url = "${api.giphy-api.url}", primary = false)
public interface FeignGiphyClient {

    @GetMapping(path = "/v1/gifs/translate", produces = MediaType.APPLICATION_JSON_VALUE)
    GiphyObject getRandomGiphyObjectByWealthTag(@RequestParam(name = "s") WealthStatus tag);

}
