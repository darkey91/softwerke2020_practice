package softwerke.scr.impl;


import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import softwerke.scr.api.Hello;

@SuppressWarnings("deprecation")
@Component
@Service(value=Hello.class)
public class HelloImpl implements Hello {
    @Override
    public void greet() {
        System.out.println("Hello, OSGI world!");
    }

    @Activate
    protected void onActivate() {
        System.out.println("HelloComponent activated..");
    }

}
