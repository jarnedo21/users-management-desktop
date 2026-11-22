package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

/** Fila de la tabla cliente (codigo, nombre, direccion, telefono, persona_contacto, tipo_actividad). */
public record ClientEntity(
    String code,
    String name,
    String address,
    String phone,
    String contactPerson,
    String activityType) {}
