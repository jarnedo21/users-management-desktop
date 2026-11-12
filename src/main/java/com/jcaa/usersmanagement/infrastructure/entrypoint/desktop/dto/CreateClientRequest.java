package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record CreateClientRequest(
    String code,
    String name,
    String address,
    String phone,
    String contactPerson,
    String activityType) {}
