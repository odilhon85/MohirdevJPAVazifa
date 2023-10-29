package uz.devior.mohirdev_jpa_vazifa.shared;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Department {
    HR("Xodimlarni boshqarish bo'limi"),
    CLIENT("Mijozlar bilan ishlash bo'limi");

    final String departmentName;
    Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getdepartmentName() {
        return this.departmentName;
    }

    @JsonCreator
    public static Department getDepartmentFromCode(String value) {

        for (Department dep : Department.values()) {
            if (dep.getdepartmentName().equals(value)) {
                return dep;
            }
        }

        return null;
    }
}
