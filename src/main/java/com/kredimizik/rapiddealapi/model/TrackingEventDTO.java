package com.kredimizik.rapiddealapi.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrackingEventDTO {

    private Long eventId;

    @Size(max = 255)
    private String eventType;

    private LocalDateTime eventDate;

    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String description;

    @NotNull
    private Long shipment;

}
