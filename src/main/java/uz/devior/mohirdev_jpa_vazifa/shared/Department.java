package uz.devior.mohirdev_jpa_vazifa.shared;

public enum Department {
    HR("Xodimlarni boshqarish bo'limi"),
    CLIENT("Mijozlar bilan ishlash bo'limi");

    final String departmentName;
    Department(String departmentName) {
        this.departmentName = departmentName;
    }
}
