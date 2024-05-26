package org.example.arbitragedemo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BinanceDto(
        @JsonProperty(value = "b")
        String bidPrice,
        @JsonProperty(value = "a")
        String askPrice
) {
}
