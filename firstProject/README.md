# Simple OSGI service
1. "Hello" bundle contains Hello interface, implementation of this interface and uses the provided bundle context to register it as 
a service in the service registry.
2. "HelloClient" bundle client searches available specified service in the registry to use it.