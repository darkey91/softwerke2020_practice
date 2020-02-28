package softwerke.first.impl;

import softwerke.first.api.Hello;

public class HelloImpl implements Hello {
    private final String name;

    public HelloImpl() {
        this.name = "OSGi";
    }

    public HelloImpl(String name) {
        this.name = name;
    }

    public void greet() {
        System.out.println("Hello " + name + " World!");
    }
}
