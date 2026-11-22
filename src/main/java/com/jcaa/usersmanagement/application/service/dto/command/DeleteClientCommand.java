package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;

public record DeleteClientCommand(
    @NotBlank(message = "code must not be blank") String code
) {

}
