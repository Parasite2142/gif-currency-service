package ru.alpha.example.gifcurrencygradle.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyObject {

    @JsonProperty(value = "data")
    private GiphyData data;

    public String getId() {
        return data.getId();
    }

    @Data
    private static class GiphyData {
        //        private String type;
        private String id;
    }
}
