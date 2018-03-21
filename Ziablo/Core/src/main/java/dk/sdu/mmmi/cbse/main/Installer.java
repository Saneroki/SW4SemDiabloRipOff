package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

//the old "Main" class is now "Installer"
public class Installer extends ModuleInstall {

    @Override
    public void restored() {

                //main method must be copied here
                LwjglApplicationConfiguration cfg
                        = new LwjglApplicationConfiguration();
                cfg.title = "Asteroids";
                cfg.width = 500;
                cfg.height = 400;
                cfg.useGL30 = false;
                cfg.resizable = false;

                new LwjglApplication(new Game(), cfg);
                System.out.println("HELOOOOOOOO FROM CORE INSTALLER");
            }

    
    // private final static e = Thread(); //i saw somekind of thread runner in Jan's example
//	public static void main(String[] args) {
//		
//		LwjglApplicationConfiguration cfg =
//			new LwjglApplicationConfiguration();
//		cfg.title = "Asteroids";
//		cfg.width = 500;
//		cfg.height = 400;
//		cfg.useGL30 = false;
//		cfg.resizable = false;
//		
//		new LwjglApplication(new Game(), cfg);
//		
//	}

}
