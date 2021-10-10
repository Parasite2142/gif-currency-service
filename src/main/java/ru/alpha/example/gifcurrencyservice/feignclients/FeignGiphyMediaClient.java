package ru.alpha.example.gifcurrencyservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "giphyMediaClient", url = "${api.giphy-media.url}")
public interface FeignGiphyMediaClient {

    @GetMapping(path = "/media/{id}/giphy.gif", produces = MediaType.IMAGE_GIF_VALUE)
    byte[] getGifById(@PathVariable String id);

}
