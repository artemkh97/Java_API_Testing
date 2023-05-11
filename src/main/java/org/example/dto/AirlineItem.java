package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(staticName = "of")
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirlineItem {
    @JsonProperty("__v")
    @Builder.Default
    private int v = 0;
    private String established;
    private String country;
    private String website;
    private String name;
    @JsonProperty("head_quaters")
    private String headQuaters;
    private String logo;
    @JsonProperty("_id")
    private String id;

    @JsonProperty("id")
    private int identification;
    private String slogan;
}
