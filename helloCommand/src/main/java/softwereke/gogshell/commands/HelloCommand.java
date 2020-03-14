package softwereke.gogshell.commands;


import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.service.command.Descriptor;

@SuppressWarnings("deprecation")
@Component
@Service(value = Object.class)
@Properties({
        @Property(name = "osgi.command.scope", value = "practice"),
        @Property(name = "osgi.command.function", value = "hello")
})
public class HelloCommand {

    @Descriptor("Simple command that takes <params> and prints `Hello, <params>`")
    public void hello(String target) {
        System.out.println("Hello, " + target);
    }
}
