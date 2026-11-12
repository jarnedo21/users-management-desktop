package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record UpdateClientRequest(
    String code,
    String name,
    String address,
    String phone,
    String contactPerson,
    String activityType) {}
