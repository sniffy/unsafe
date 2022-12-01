# unsafe

### Structure



### Naming conventions

#### Class names

`Instance*` - non-static members (fields and methods) with a reference to particular object
`Static*` - static members (fields and methods)
`NonStatic*` - non-static members (fields and methods) without a reference to particular object; require passing an object when working with them

`Unresolved*` - unresolved references which might (or might not) throw an `UnresolvedRefException` when working with them in case say class or methog were not found

#### Fluent API

function `$` does the magic depending on context - i.e. creates `ClassRef` or `ObjectRef` instances for given Class, class name or arbitrary object, or creates a new `ObjectRef` instance for the given object and field name.

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