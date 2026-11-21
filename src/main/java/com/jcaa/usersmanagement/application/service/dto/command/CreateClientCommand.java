package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientCommand(
    @NotBlank(message = "code must not be blank")
        @Size(max = 10, message = "code must have at most 10 characters")
        String code,
    @NotBlank(message = "name must not be blank")
        @Size(min = 3, message = "name must have at least 3 characters")
        String name,
    @NotBlank(message = "address must not be blank") String address,
    @NotBlank(message = "phone must not be blank") String phone,
    @NotBlank(message = "contact person must not be blank") String contactPerson,
    @NotBlank(message = "activity type must not be blank") String activityType)
{

}
