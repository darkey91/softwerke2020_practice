package softwerke.client;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferencePolicy;
import softwerke.scr.api.Hello;

@SuppressWarnings("deprecation")
@Component
public class HelloUser {

    @Reference(policy = ReferencePolicy.DYNAMIC)
    private volatile Hello helloService;

    @Activate
    public void greetThroughOther() {
        System.out.println("HelloUser activated!");

        helloService.greet();
    }


}