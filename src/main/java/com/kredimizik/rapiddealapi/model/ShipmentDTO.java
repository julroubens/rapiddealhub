package com.kredimizik.rapiddealapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShipmentDTO {

    private Long shipmentId;

    @Size(max = 255)
    private String trackingNumber;

    @Schema(type = "string", example = "18:30")
    private LocalTime shimentDate;

    @Schema(type = "string", example = "18:30")
    private LocalTime deliveryDate;

    @Size(max = 255)
    private String status;

    @Size(max = 50)
    private String carrier;

    private Long box;

}
