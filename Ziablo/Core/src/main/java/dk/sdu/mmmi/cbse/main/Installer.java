package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {

                //main method must be copied here
                LwjglApplicationConfiguration cfg
                        = new LwjglApplicationConfiguration();
                cfg.title = "Ziablo";
                cfg.width = 1024;
                cfg.height = 768;
                cfg.useGL30 = false;
                cfg.resizable = false;

                new LwjglApplication(new Game(), cfg);
                System.out.println("Core restored");
            }
}
