package example.processor;

import example.ann.GenerateFriend;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes({"example.ann.GenerateFriend"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class EventHolderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(Element element : roundEnv.getElementsAnnotatedWith(GenerateFriend.class)){
            GenerateFriend annotation = element.getAnnotation(GenerateFriend.class);
            processAnnotation(element, annotation);
        }

        return true;
    }

    private void processAnnotation(Element element, GenerateFriend annotation) {
        String className = annotation.name();
        String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        try{
            generateClass(packageName, className);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void generateClass(String packageName, String className) throws Exception {
        JavaFileObject file = processingEnv.getFiler().createSourceFile(packageName + "." + className);
        try(PrintWriter writer = new PrintWriter(file.openWriter())){
            writer.println("package " + packageName + ";");
            writer.println();
            writer.println("public class " + className + " {");
            writer.println("}");
        }
    }
}
