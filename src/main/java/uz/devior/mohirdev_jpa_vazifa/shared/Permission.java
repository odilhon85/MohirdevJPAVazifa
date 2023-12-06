package uz.devior.mohirdev_jpa_vazifa.shared;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    DIRECTOR_READ("director:read"),
    DIRECTOR_CREATE("director:create"),
    DIRECTOR_UPDATE("director:update"),
    DIRECTOR_DELETE("director:delete"),

    DEPARTMENT_HEAD_READ("departmentHead:read"),
    DEPARTMENT_HEAD_UPDATE("departmentHead:update"),
    DEPARTMENT_HEAD_CREATE("departmentHead:create"),
    DEPARTMENT_HEAD_DELETE("departmentHead:delete"),

    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_CREATE("employee:create"),
    EMPLOYEE_UPDATE("employee:update");

    private final String permission;
}
