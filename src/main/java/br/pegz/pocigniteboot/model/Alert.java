package br.pegz.pocigniteboot.model;

import lombok.Value;

import java.util.UUID;

@Value
public class Alert {

    private UUID uuid;

    private String message;

    private String email;

    private Long priority;
}
