import com.sun.source.tree.*;
import com.sun.source.util.JavacTask;
import java.nio.file.*;
import java.util.*;
import javax.lang.model.element.Modifier;
import javax.tools.*;
import javax.xml.stream.*;

class InspectDiagram {
    public static void main(String[] args) throws Exception {
        var compiler = ToolProvider.getSystemJavaCompiler();
        try (var manager = compiler.getStandardFileManager(null, null, null);
             var paths = Files.walk(Path.of("src"))) {
            var files = paths.filter(p -> p.toString().endsWith(".java")).toList();
            var task = (JavacTask) compiler.getTask(null, manager, null, List.of("-proc:none"), null,
                    manager.getJavaFileObjectsFromPaths(files));
            var xml = XMLOutputFactory.newFactory().createXMLStreamWriter(System.out);
            xml.writeStartElement("classes");
            for (var unit : task.parse()) for (var node : unit.getTypeDecls()) {
                if (!(node instanceof ClassTree type)) continue;
                String name = type.getSimpleName().toString();
                xml.writeStartElement("class");
                xml.writeAttribute("name", name);
                xml.writeAttribute("file", unit.getSourceFile().getName());
                xml.writeAttribute("kind", type.getKind().toString());
                if (type.getExtendsClause() != null) xml.writeAttribute("extends", type.getExtendsClause().toString());
                xml.writeAttribute("implements", type.getImplementsClause().toString());
                for (var member : type.getMembers()) {
                    String value = null;
                    if (member instanceof VariableTree f) {
                        boolean constant = type.getKind() == Tree.Kind.ENUM && f.getInitializer() instanceof NewClassTree;
                        value = constant ? f.getName().toString() :
                                (f.getModifiers().getFlags().contains(Modifier.PRIVATE) ? "-" : "+")
                                + f.getName() + ": " + f.getType();
                        if (!constant && f.getInitializer() instanceof LiteralTree
                                && f.getModifiers().getFlags().contains(Modifier.FINAL)) value += " = " + f.getInitializer();
                    } else if (member instanceof MethodTree m
                            && (m.getModifiers().getFlags().contains(Modifier.PUBLIC) || type.getKind() == Tree.Kind.INTERFACE)) {
                        var parameters = new ArrayList<String>();
                        for (var p : m.getParameters()) parameters.add(p.getName() + ": " + p.getType());
                        value = "+" + (m.getReturnType() == null ? name : m.getName())
                                + "(" + String.join(", ", parameters) + ")"
                                + (m.getReturnType() == null ? "" : ": " + m.getReturnType());
                    }
                    if (value != null) {
                        xml.writeStartElement("row"); xml.writeAttribute("text", value); xml.writeEndElement();
                    }
                }
                xml.writeEndElement();
            }
            xml.writeEndElement(); xml.close();
        }
    }
}
