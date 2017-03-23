package Logger.Logger;

import java.util.*;

/**
 * This class is dedicated as a reference class for the Systems to be logged. Up to 10 systems are supported at
 * this time. The list below can be added to or removed from as needed. Just adjust the port numbers and the
 * address in connect_to_systems() in Logger.java as needed and the application will take care of the rest.
 */
public class SystemsList {

    public final List<System> systems = new ArrayList<>();

    public SystemsList() {
        //System ID and name can be whatever you want. The application will not be affected by changing them.
        systems.add(new System(0, "System_0", 32123));
        systems.add(new System(1, "System_1", 32123));
        systems.add(new System(2, "System_2", 32123));
        systems.add(new System(3, "System_3", 32123));
        systems.add(new System(4, "System_4", 32123));
        systems.add(new System(5, "System_5", 32123));

    }

    public class System {

        public final int id;
        public final String name;
        public final int port;

        public System(int ID, String sysName, int portNum) {
            id = ID;
            name = sysName;
            port = portNum;
        }

        @Override
        public String toString() {
            return name + " - " + port;
        }
    }
}
