package uz.devior.mohirdev_jpa_vazifa.shared;

public enum Position {
    DIRECTOR("Direktor"),
    DEPARTMENT_HEAD("Bo'lim boshlig'i"),
    EMPLOYEE("Xodim");

    final String position;
    Position(String position){
        this.position = position;
    }
}
