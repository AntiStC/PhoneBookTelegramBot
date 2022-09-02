package ru.spb.sspk.ssdmd.phonebook.bot;

public enum EnumCommandBot {

    INFO("/info"),

    START("/start"),

    FIND("/find"),

    CREATE("/create"),

    UPDATE("/update"),

    DELETE("/delete"),

    DROP("/drop");

    private String command;

    EnumCommandBot(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
