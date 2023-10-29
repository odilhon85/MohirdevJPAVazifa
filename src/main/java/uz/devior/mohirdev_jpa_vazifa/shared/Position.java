package uz.devior.mohirdev_jpa_vazifa.shared;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Position {
    DIRECTOR("Direktor"),
    DEPARTMENT_HEAD("Bo'lim boshlig'i"),
    EMPLOYEE("Xodim");

    final String position;
    Position(String position){
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }

    @JsonCreator
    public static Position getPositionFromCode(String value) {

        for (Position position : Position.values()) {
            if (position.getPosition().equals(value)) {
                return position;
            }
        }

        return null;
    }
}
