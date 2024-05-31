package com.kredimizik.rapiddealapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionDTO {

    private Long transactionId;

    private Double amount;

    private Double dept;

    @Schema(type = "string", example = "18:30")
    private LocalTime transactionDate;

    @Size(max = 255)
    private String paymentMethod;

    @Size(max = 255)
    private String status;

    @NotNull
    private Long client;

    @NotNull
    private Long shipment;

}
