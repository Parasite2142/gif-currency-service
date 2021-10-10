package ru.alpha.example.gifcurrencyservice.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyObject {

    @JsonProperty(value = "data")
    private GiphyData data;

    public String getId() {
        return data.getId();
    }

    public static GiphyObject create(String id) {
        return GiphyObject.create(GiphyData.create(id));
    }

    @Data
    @AllArgsConstructor(staticName = "create")
    @NoArgsConstructor
    private static class GiphyData {
        private String id;
    }

}
