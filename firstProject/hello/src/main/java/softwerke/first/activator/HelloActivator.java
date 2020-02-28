package softwerke.first.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import softwerke.first.api.Hello;
import softwerke.first.impl.HelloImpl;

public class HelloActivator implements BundleActivator {
    private final String LOG_TAG = "HelloActivator";
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println(LOG_TAG + ": Started to register service..");
        bundleContext.registerService(Hello.class.getName(),
                new HelloImpl(), null);
        System.out.println(LOG_TAG + ": Finished to register service..");
    }

    public void stop(BundleContext bundleContext) throws Exception {}
}
