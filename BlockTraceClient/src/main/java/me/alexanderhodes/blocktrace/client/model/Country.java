package me.alexanderhodes.blocktrace.client.model;

/**
 * Created by alexa on 24.09.2017.
 */
public class Country {

    private String name;
    private String code;

    public Country () {

    }

    public Country (String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
