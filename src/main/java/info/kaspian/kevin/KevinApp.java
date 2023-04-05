package info.kaspian.kevin;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class KevinApp implements QuarkusApplication {
    @Override
    public int run(String... args) {
        Quarkus.waitForExit();
        return 0;
    }
}
