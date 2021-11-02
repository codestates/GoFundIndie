package com.IndieAn.GoFundIndie.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HealthDTO {
    private String server;
    private String health;

    public HealthDTO() {}
}
