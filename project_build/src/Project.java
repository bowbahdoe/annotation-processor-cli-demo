import dev.mccue.tools.java.Java;
import dev.mccue.tools.javac.Javac;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;

@CommandLine.Command(name = "project")
public class Project {
    final Path buildDir = Path.of("build");
    final Path javacOutDir = buildDir.resolve("javac");
    final Path generatedSourcesDir = buildDir.resolve("generated");

    @CommandLine.Command
    public void compile() throws Exception {
        if (Files.exists(buildDir)) {
            FileUtils.deleteDirectory(buildDir.toFile());
        }
        Javac.run(arguments -> {
            arguments._d(javacOutDir)
                    .__module_source_path("./modules/*/src")
                    .__module("example.processor,example.ann")
                    .__release(17);
        });

        Javac.run(arguments -> {
            arguments._d(javacOutDir)
                    ._s(generatedSourcesDir)
                    .__module_path(javacOutDir)
                    .__processor_module_path(javacOutDir)
                    .__module_source_path("./modules/*/src")
                    .__module("example.usage")
                    .__release(17);
        });
    }

    @CommandLine.Command
    public void run() throws Exception {
        compile();
        Java.run(arguments -> {
            arguments.__module_path(javacOutDir)
                    ._m("example.usage/apple.Example");
        });
    }
    public static void main(String[] args) {
        new CommandLine(new Project()).execute(args);
    }
}
