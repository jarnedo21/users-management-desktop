package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetClientByCodeQuery(@NotBlank(message = "code must not be blank") String code)
{

}
