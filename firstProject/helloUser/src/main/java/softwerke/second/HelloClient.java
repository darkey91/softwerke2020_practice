package softwerke.second;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import softwerke.first.api.Hello;

public class HelloClient implements BundleActivator {
    private final String LOG_TAG = "HelloClient";

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println(LOG_TAG + ": Trying to get registered service..");
        var helloServiceRef = bundleContext.getServiceReference(Hello.class.getName());
        if (helloServiceRef != null) {
            var service = ((Hello) bundleContext.getService(helloServiceRef));
            if (service != null) {
                service.greet();
            } else {
                System.err.println("Can't get service object");
            }
        } else {
            System.err.println("Can't get ServiceReference object for a service named " +  Hello.class.getName() +
                    ".\nPossible reason of problem: service hasn't been registered yet");
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    }
}
