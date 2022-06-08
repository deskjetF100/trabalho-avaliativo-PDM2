package com.example.trabalhoavaliativopdm2.pokemon;

public class Pokemon {
    String name;
    String front_default;
    int base_experience;

    public Pokemon(String name, String front_default, int base_experience) {
        this.name = name;
        this.front_default = front_default;
        this.base_experience = base_experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", front_default='" + front_default + '\'' +
                ", base_experience=" + base_experience +
                '}';
    }
}
