package dk.sdu.mmmi.cbse.ziablo;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Lookup;


// Application Integration Test
public class ApplicationTest extends NbTestCase {
    // updates.xml files that have different configurations
    private static final String NO_MODULES = "C:\\netbeans_site\\configurations\\NO_MODULES\\updates.xml";
    private static final String ALL_MODULES = "C:\\netbeans_site\\configurations\\ALL_MODULES\\updates.xml";
    private static final String UPDATES_File = "C:\\netbeans_site\\updates.xml";
            
            
    public static Test suite() {
        return NbModuleSuite.createConfiguration(ApplicationTest.class).
                gui(false).
                failOnMessage(Level.WARNING). // works at least in RELEASE71
                failOnException(Level.INFO).
                enableClasspathModules(false). 
                clusters(".*").
                suite(); // RELEASE71+, else use NbModuleSuite.create(NbModuleSuite.createConfiguration(...))
    }

    public ApplicationTest(String n) {
        super(n);
    }
    
    
    public void testDynamicLoadingOfModules() throws InterruptedException, IOException {
        
        
        //Setup to test if the components can be loaded and unloaded
        List<IEntityProcessingService> processors = new CopyOnWriteArrayList<>();
        List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
        waitForUpdate(processors, plugins);
        
        // Pre Asserts
        // Size should be 6 because the Game should start with all modules
        assertEquals("All Plugins installed", 7, plugins.size());
        assertEquals("All processors installed", 6, processors.size());
        
        // Test: Unload all modules via Update Center
        copy(get(NO_MODULES), get(UPDATES_File), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        
        // Assert all modules are uninstalled
        assertEquals("No plugins installed", 0, plugins.size());
        assertEquals("No processors installed", 0, processors.size());
        
        // Test: Load all modules via Update Center
        copy(get(ALL_MODULES), get(UPDATES_File), REPLACE_EXISTING);
        waitForUpdate(processors, plugins);
        
        
        // Size should be 6 because the Game should start with all modules
        assertEquals("All Plugins reloaded", 7, plugins.size());
        assertEquals("All processors reloaded", 6, processors.size());
    }
    
    private void waitForUpdate(List<IEntityProcessingService> processors, List<IGamePluginService> plugins) throws InterruptedException{
        // gives time for silentUpdater to install all modules
        Thread.sleep(10000); // 10 seconds
        processors.clear();
        processors.addAll(Lookup.getDefault().lookupAll(IEntityProcessingService.class));
        
        plugins.clear();
        plugins.addAll(Lookup.getDefault().lookupAll(IGamePluginService.class));
    }
}
