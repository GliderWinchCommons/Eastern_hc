package Logger.Logger;

import java.util.*;

/**
 * Created by gm14793 on 1/30/17.
 */
public class SystemsList {

    public final List<System> systems = new ArrayList<>();

    //public final Map<String, String> systems = new HashMap<>();
    public SystemsList() {
        systems.add(new System(0, "System_A", 32123));
        systems.add(new System(10, "System_B", 32123));
        systems.add(new System(11, "System_C", 32123));
        systems.add(new System(12, "System_D", 32123));
        systems.add(new System(13, "System_E", 32123));
        systems.add(new System(14, "System_F", 32123));

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
