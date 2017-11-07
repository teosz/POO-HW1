# OOP Homework 1

## Prerequisite

  - [Flux architecture](https://facebook.github.io/flux/)
  - [Predictable state containers](http://paulserraino.com/javascript/2016/04/21/state-containers.html)

## Dependency

Serialization of object from config is made with GSON (`com/gson`).
Because is a lot more clean to keep all the game related data into JSON rather than hardcoding them.
First atempt was to use native Oracle Nashorn for JSON parsing but is not stabile and the documentation is unsynchronized with the code.

## Game config

The configuration of the whole game is stored in `loo/config.json`.
All the heros and their spells are defined there.

 ### Hero fields

| Field  | description  |
|---|---|
|`baseHP`|  hero health at the current level |
|`currentHP`|  the health with which hero will be instantiated |
|`levelupHP`| which will be added to the base on when level up  |
|`spells`| list of spells |
|`ability`| specifying the modifier and the terrain where this ability is enabled |

### Spell fields

| Field  | description  |
|---|---|
|`name`|  spell name |
|`baseDamage`|  spell damage at the current level |
|`levelupDamage`| which will be added to the base on when level up  |
|`modifiers`| defining the modifier of the damage based on the opponent |
|`options`| bunch of options which can be accessed in the code |

### Config example
```json
{
  "MyAswomeHero": {
    "baseHP": 420,
    "currentHP": 420,
    "levelupHP": 42,
    "spells": [{
      "name": "super_spell",
      "baseDamage": 420,
      "levelupDamage": 20,
      "modifiers": {
        "OtherHero":	-10,
      },
      "options": {
        "freezeRounds": 3,
      }
    }],
    "ability": {
      "terrain": "Land",
      "modifier": 42
    }
  }
}
```
### Application

Apllication State is composed by a list of StateCell
StateCell containts a hero and their position.


At each rounds analyze the current state and dispatch all the actions to be made (add hero on map, hero spells, movement, round start/end) which are handled by:
  - `SpellManagement` - if the are related to spells
  - `MapManagement` - if the are related to map
  - `RoundManagement` - if the are related to round

#### Scheme

  ```           
            disptach
+---------------------------+
|                           |
|                           |
|                           |
|                           |
|                           |
|                           |
|                 +---------v---------+
|                 |                   |
|                 |                   |
|                 |      Reducer      |
|                 |                   |
|                 +---------+---------+
|                           |
|                           |
|                 +---------v---------+
|                 |                   |
|                 |       State       |
|                 +---------+---------+
|                           |
|                           |
|                           v
|                 +---------+---------+
|                 |                   |
+-----------------+        Game       |
"                 |                   |
"                 +---------+---------+
```

Advantages
  - Single point of mutation
  - Unidirectional data flow

### Bonus

Since I do not use inheritance I do not have to deal with upcasting.
The only matching I need is action type matching which is done like in the original [redux](https://github.com/reactjs/redux/blob/master/examples/counter/src/reducers/index.js#L3) implementation.

### Post Insights

This kind of homework encourages students to make a mistake I see frequently in code: data abstraction should dictates how code looks like not the other way around: the only reason to make a subclasses is is that code looks more beautiful but this is not correct from a conceptual point of view.

`John` is an instance (not an extension) of the class Human, analogously `Wizard` is an **instance** of `Hero`.

In a world with only apples class `Fruit` does not make sense.
If there would be two heros `Harry Potter` and `Voldemort` with different properties then a subclass for `Wizard` would make sense.

Also Java 8 and even C++ 11 have elements of functional programming. I think is important the way design patterns are presented in this context:
https://news.ycombinator.com/item?id=4672380


License
----

MIT
