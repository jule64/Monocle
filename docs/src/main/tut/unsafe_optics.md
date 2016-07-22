---
layout: default
title:  "Unsafe Optics"
section: "optics"
pageSource: "https://raw.githubusercontent.com/julien-truffaut/Monocle/master/docs/src/main/tut/unsafe_optics.md"
---
# Background

All the Optics provided in the `core` module obey a well defined set of `Laws` that make these Optics safe to use for all possible cases.  

In some cases of Optics however it is not always possible to make such guarantees, in which case the Optic is said to be "unsafe".  

This is not to say that these special cases cannot be used - they actually come in handy on many occasions - but care must be taken in using them as we will demonstrate in the next sections.  


# The `unsafe` module

All Optics considered unsafe are provided in the `unsafe` module.  This module contains the following Optics:

- UnsafeSelect
- UnsafeHCompose


## UnsafeSelect

`UnsafeSelect` allows to create a `Prism` based on a predicate. Let's have a look at a simple example:

```tut:silent
case class Person(name: String, age: Int)
```

Using an `UnsafeSelect` we can select all `Person` with `age >= 18` and then use a `Lens` to modify their name:

```tut:silent
UnsafeSelect.unsafeSelect[Person](_.age >= 18) composeLens GenLens[Person](_.name).modify("Adult " + _)

```

This operator is considered unsafe because it allows for inconsistency if a `Lens` is then used to change one of the value used in the predicates. For example:

```tut:silent
UnsafeSelect.unsafeSelect[Person](_.age >= 18) composeLens GenLens[Person](_.age).set(0)
```

In this example the age is reset to `0` which invalidates the original predicate of `age >= 18`. More formally `UnsafeSelect` can invalidate the `roundTripOtherWayLaw` law.

## UnsafeHCompose

`UnsafeHCompose` offers the ability to use a `Traversal` Optic with *any* number of `Lens` (0 to n).  

This is a case of unsafe Optic because it is not possible to know if two (or more) `Lens` will modify the same value, hence breaking the `composeModify` law of `TraversalLaws`.





