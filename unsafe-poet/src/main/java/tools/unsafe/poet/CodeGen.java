package tools.unsafe.poet;

import com.squareup.javapoet.*;
import jdk.nashorn.internal.codegen.types.Type;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CodeGen {

    public static void main(String[] args) throws IOException {

        MethodSpec invoke = MethodSpec.
                methodBuilder("invoke").
                beginControlFlow("try").
                addStatement("getAccessibleMethod().invoke(instance, p1)").
                nextControlFlow("catch($T e)", InvocationTargetException.class).
                addStatement("throw e").
                nextControlFlow("catch($T e)", Exception.class).
                addStatement("throw new UnsafeInvocationException(e)").
                endControlFlow().
                addModifiers(Modifier.PUBLIC).
                addParameter(TypeVariableName.get("C"), "instance").
                addParameter(TypeVariableName.get("P1"), "p1").
                build();

        TypeSpec resolvedVoidDynamicOneParamMethodRef = TypeSpec.
                classBuilder("ResolvedVoidDynamicOneParamMethodRef").
                addTypeVariable(TypeVariableName.get("C")).
                addTypeVariable(TypeVariableName.get("P1")).
                superclass(ClassName.bestGuess("tools.unsafe.reflection.method.AbstractMethodRef<C>")).
                addSuperinterface(ClassName.bestGuess("tools.unsafe.reflection.method.voidresult.oneparam.GenericVoidDynamicOneParamMethodRef<C, P1>")).
                addModifiers(Modifier.PUBLIC).
                addMethod(invoke).
                build();

        JavaFile javaFile = JavaFile
                .builder("tools.unsafe.reflection.method.voidresult.oneparam.resolved", resolvedVoidDynamicOneParamMethodRef)
                .indent("    ")
                .build();

        javaFile.writeTo(System.out);

    }

}
