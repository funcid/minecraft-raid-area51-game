package ru.func.raidarea;

public enum RaidTimeStatus {

    WAITING(10, "Ожидание"),
    STARTING(20, "Начало"),
    GAME(600, "Игра"),
    ENDING(615, "Завершение"),
    ;

    private int    time;
    private String name;

    RaidTimeStatus(final int time, final String name) {
        this.time = time;
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
