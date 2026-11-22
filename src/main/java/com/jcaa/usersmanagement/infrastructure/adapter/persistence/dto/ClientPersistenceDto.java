package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

public record ClientPersistenceDto(
    String code,
    String name,
    String address,
    String phone,
    String contactPerson,
    String activityType) {}
