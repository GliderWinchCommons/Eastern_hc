/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseUtilities;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gene
 */
public class DatabaseInitializationTest {
    
    private Connection connection;
    
    @Before
    public void setUp() {
        String databaseConnectionName = "jdbc:derby:TestDB;create=true";
        //Try to connect to the specified database
        try{
            connection = DriverManager.getConnection(databaseConnectionName);
        }catch(SQLException e) {
            System.out.println("I'm not sure what went wrong\n" + e.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        try{
            connection.close();
        }catch(SQLException e) {
            System.out.println("I'm not sure what went wrong\n" + e.getMessage());
        }
    }

    /**
     * Test of connect method, of class DatabaseInitialization.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        Connection expResult = null;
        Connection result = DatabaseInitialization.connect();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of connectEx method, of class DatabaseInitialization.
     */
    @Test
    public void testConnectEx() throws Exception {
        System.out.println("connectEx");
        Connection expResult = null;
        Connection result = DatabaseInitialization.connectEx();
        assertNotEquals(expResult, result);
    }
    
    /**
     * Test of createVersion method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateVersion() throws Exception {
        System.out.println("createVersion");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method version = c.getDeclaredMethod("createVersion", paramTypes);
        Method dropVersion = c.getDeclaredMethod("dropVersion", paramTypes);
        
        version.setAccessible(true);
        dropVersion.setAccessible(true);
        
        result = (boolean)version.invoke(c, connection);
        dropVersion.invoke(c, connection);
        
        assertEquals(true, result);
    }

    /**
     * Test of createPilot method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreatePilot() throws Exception {
        System.out.println("createPilot");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method pilot = c.getDeclaredMethod("createPilot", paramTypes);
        Method capability = c.getDeclaredMethod("createCapability", paramTypes);
        Method dropPilot = c.getDeclaredMethod("dropPilot", paramTypes);
        Method dropCapability = c.getDeclaredMethod("dropCapabliity", paramTypes);
        
        pilot.setAccessible(true);
        capability.setAccessible(true);
        dropPilot.setAccessible(true);
        dropCapability.setAccessible(true);
        
        capability.invoke(c, connection);
        result = (boolean)pilot.invoke(c, connection);
        dropPilot.invoke(c, connection);
        dropCapability.invoke(c, connection);
        assertEquals(true, result);
    }
    
    /**
     * Test of createGlider method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateGlider() throws Exception {
        System.out.println("createGlider");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method method = c.getDeclaredMethod("createGlider", paramTypes);
        Method dropMethod = c.getDeclaredMethod("dropGlider", paramTypes);
        
        method.setAccessible(true);
        dropMethod.setAccessible(true);
        
        result = (boolean)method.invoke(c, connection);
        dropMethod.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createCapability method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateCapability() throws Exception {
        System.out.println("createCapability");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method method = c.getDeclaredMethod("createCapability", paramTypes);
        Method dropMethod = c.getDeclaredMethod("dropCapabliity", paramTypes);
        
        method.setAccessible(true);
        dropMethod.setAccessible(true);
        
        result = (boolean)method.invoke(c, connection);
        dropMethod.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createAirfield method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateAirfield() throws Exception {
        System.out.println("createAirfield");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method method = c.getDeclaredMethod("createAirfield", paramTypes);
        Method dropMethod = c.getDeclaredMethod("dropAirfield", paramTypes);
        
        method.setAccessible(true);
        dropMethod.setAccessible(true);
        
        result = (boolean)method.invoke(c, connection);
        dropMethod.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createRunway method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateRunway() throws Exception {
        System.out.println("createRunway");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method runway = c.getDeclaredMethod("createRunway", paramTypes);
        Method dropRunway = c.getDeclaredMethod("dropRunway", paramTypes);
        Method airfield = c.getDeclaredMethod("createAirfield", paramTypes);
        Method dropAirfield = c.getDeclaredMethod("dropAirfield", paramTypes);
        
        runway.setAccessible(true);
        dropRunway.setAccessible(true);
        airfield.setAccessible(true);
        dropAirfield.setAccessible(true);
        
        airfield.invoke(c, connection);
        result = (boolean)runway.invoke(c, connection);
        dropRunway.invoke(c, connection);
        dropAirfield.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createGliderPosition method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateGliderPosition() throws Exception {
        System.out.println("createGliderPosition");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method runway = c.getDeclaredMethod("createRunway", paramTypes);
        Method dropRunway = c.getDeclaredMethod("dropRunway", paramTypes);
        Method airfield = c.getDeclaredMethod("createAirfield", paramTypes);
        Method dropAirfield = c.getDeclaredMethod("dropAirfield", paramTypes);
        Method gliderPos = c.getDeclaredMethod("createGliderPosition", paramTypes);
        Method dropGliderPos = c.getDeclaredMethod("dropGliderPosition", paramTypes);
        
        runway.setAccessible(true);
        dropRunway.setAccessible(true);
        airfield.setAccessible(true);
        dropAirfield.setAccessible(true);
        gliderPos.setAccessible(true);
        dropGliderPos.setAccessible(true);
        
        airfield.invoke(c, connection);
        runway.invoke(c, connection);
        result = (boolean)gliderPos.invoke(c, connection);
        dropGliderPos.invoke(c, connection);
        dropRunway.invoke(c, connection);
        dropAirfield.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createWinchPosition method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateWinchPosition() throws Exception {
        System.out.println("createWinchPosition");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method runway = c.getDeclaredMethod("createRunway", paramTypes);
        Method dropRunway = c.getDeclaredMethod("dropRunway", paramTypes);
        Method airfield = c.getDeclaredMethod("createAirfield", paramTypes);
        Method dropAirfield = c.getDeclaredMethod("dropAirfield", paramTypes);
        Method winchPos = c.getDeclaredMethod("createWinchPosition", paramTypes);
        Method dropWinchPos = c.getDeclaredMethod("dropWinchPosition", paramTypes);
        
        runway.setAccessible(true);
        dropRunway.setAccessible(true);
        airfield.setAccessible(true);
        dropAirfield.setAccessible(true);
        winchPos.setAccessible(true);
        dropWinchPos.setAccessible(true);
        
        airfield.invoke(c, connection);
        runway.invoke(c, connection);
        result = (boolean)winchPos.invoke(c, connection);
        dropWinchPos.invoke(c, connection);
        dropRunway.invoke(c, connection);
        dropAirfield.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createWinch method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateWinch() throws Exception {
        System.out.println("createWinch");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method method = c.getDeclaredMethod("createWinch", paramTypes);
        Method dropMethod = c.getDeclaredMethod("dropWinch", paramTypes);
        
        method.setAccessible(true);
        dropMethod.setAccessible(true);
        
        result = (boolean)method.invoke(c, connection);
        dropMethod.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createDrum method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateDrum() throws Exception {
        System.out.println("createDrum");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method drum = c.getDeclaredMethod("createDrum", paramTypes);
        Method dropDrum = c.getDeclaredMethod("dropDrum", paramTypes);
        Method winch = c.getDeclaredMethod("createWinch", paramTypes);
        Method dropWinch = c.getDeclaredMethod("dropWinch", paramTypes);
        
        drum.setAccessible(true);
        dropDrum.setAccessible(true);
        winch.setAccessible(true);
        dropWinch.setAccessible(true);
        
        winch.invoke(c, connection);
        result = (boolean)drum.invoke(c, connection);
        dropDrum.invoke(c, connection);
        dropWinch.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createParachute method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateParachute() throws Exception {
        System.out.println("createParachute");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method method = c.getDeclaredMethod("createParachute", paramTypes);
        Method dropMethod = c.getDeclaredMethod("dropParachute", paramTypes);
        
        method.setAccessible(true);
        dropMethod.setAccessible(true);
        
        result = (boolean)method.invoke(c, connection);
        dropMethod.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createPreviousLaunches method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreatePreviousLaunches() throws Exception {
        System.out.println("createPreviousLaunches");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method PrevLaunches = c.getDeclaredMethod("createPreviousLaunches", paramTypes);
        Method dropPL = c.getDeclaredMethod("dropPrevLaunches", paramTypes);
        Method Pai = c.getDeclaredMethod("createPrevAirfieldInfo", paramTypes);
        Method dropPai = c.getDeclaredMethod("dropPrevAirfield", paramTypes);
        Method capability = c.getDeclaredMethod("createCapability", paramTypes);
        Method dropCapability = c.getDeclaredMethod("dropCapabliity", paramTypes);

        
        PrevLaunches.setAccessible(true);
        dropPL.setAccessible(true);
        Pai.setAccessible(true);
        dropPai.setAccessible(true);
        capability.setAccessible(true);
        dropCapability.setAccessible(true);
        
        capability.invoke(c, connection);
        Pai.invoke(c, connection);
        result = (boolean)PrevLaunches.invoke(c, connection);
        dropPL.invoke(c, connection);
        dropPai.invoke(c, connection);
        dropCapability.invoke(c, connection);

        
        assertEquals(true, result);
    }
    
    /**
     * Test of createPrevAirfieldInfo method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreatePrevAirfieldInfo() throws Exception {
        System.out.println("createPrevAirfieldInfo");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method method = c.getDeclaredMethod("createPrevAirfieldInfo", paramTypes);
        Method dropMethod = c.getDeclaredMethod("dropPrevAirfield", paramTypes);
        
        method.setAccessible(true);
        dropMethod.setAccessible(true);
        
        result = (boolean)method.invoke(c, connection);
        dropMethod.invoke(c, connection);
        
        assertEquals(true, result);
    }
    
    /**
     * Test of createOperator method, of class DatabaseInitialization.
     * @throws Exception
     */
    @Test
    public void testCreateOperator() throws Exception {
        System.out.println("createOperator");
        boolean result;
        Class c = DatabaseInitialization.class;
        Class paramTypes[] = {Connection.class};
        Method method = c.getDeclaredMethod("createOperator", paramTypes);
        Method dropMethod = c.getDeclaredMethod("dropOperator", paramTypes);
        
        method.setAccessible(true);
        dropMethod.setAccessible(true);
        
        result = (boolean)method.invoke(c, connection);
        dropMethod.invoke(c, connection);
        
        assertEquals(true, result);
    }
}
