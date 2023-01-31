# unsafe

### Vision

- many apps rely on Unsafe to deal with final or private fields via reflection in a ninja style
- This is wrong and must be addressed properly (raising incididents for librarty maintainers to open their API)
- When not possible it must be dealt with using instrumentation
- unsafe tools provide API which is firendly to static analysis
- unsafe tools provide a way forward to migrate to new JDK APIS (code evolution)
- unsafe tools provide an extremely compatible API (HotSport 5-21, J9, Android, GraalVM native images)
- unsafe tools provide a helper to run instrumentation

### Links

org.mockito.internal.util.reflection.InstrumentationMemberAccessor

### TODO

Duck Typing and mimicking
get parameter names

### GraalVM

https://docs.oracle.com/en/graalvm/enterprise/20/docs/reference-manual/native-image/Reflection/

### Structure

### Naming conventions

tools.unsafe.reflection.method.voidresult.oneparam.resolved.ResolvedVoidDynamicOneParamMethodRef<C, P1>
implements GenericVoidDynamicOneParamMethodRef<C, P1>

voidresult.oneparam.resolved.ResolvedVoidDynamicOneParamMethodRef
voidresult - result (voidresult / typedresult / genericresult)
oneparam - arguments (oneparam, twoparams, threeparams, fourparams, multipleparams)
resolved - resolved / unresolved

ResolvedVoidDynamicOneParamMethodRef
resolved - resolved / unresolved
void - void / typedresult / genericresult

ResolvedInstanceVoidOneParamMethodRef
resolved - resolved / unresolved
instance - instance / static / dynamic
void - void / types / generic
oneparam - oneparam, twoparams, threeparams, fourparams, multipleparams

#### Class names

`Instance*` - non-static members (fields and methods) with a reference to particular object
`Static*` - static members (fields and methods)
`NonStatic*` - non-static members (fields and methods) without a reference to particular object; require passing an
object when working with them

`Unresolved*` - unresolved references which might (or might not) throw an `UnresolvedRefException` when working with
them in case say class or methog were not found

#### Fluent API

function `$` does the magic depending on context - i.e. creates `ClassRef` or `ObjectRef` instances for given Class,
class name or arbitrary object, or creates a new `ObjectRef` instance for the given object and field name.

It is not the most stongly types API but might be

```java

class Bar {
    private final int baz;

    private Bar(int baz) {
        this.baz = baz;
    }
}

class Foo {
    private final static Bar STATIC_BAR_REFERENCE = new Bar(7);
    private final Bar bar;

    private Foo(int baz) {
        bar = new Bar(baz);
    }
}

    Foo foo = new Foo(42);

    $(foo).$("bar").$("baz").compareAndSet(42,13);
        $(Foo.class).field("STATIC_BAR_REFERENCE").set(3);

```