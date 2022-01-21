# BrickNametags

A nametag/tablist extension for [Minestom](https://github.com/Minestom/Minestom).

## Install

Get the latest jar file from [Github actions](https://github.com/MinestomBrick/BrickWorlds/actions) 
and place it in the extension folder of your minestom server.

Make sure to also install [BrickPlaceholders](https://github.com/MinestomBrick/BrickPlaceholders).

## Config

You can change the settings in the `config.json`.

You can remove and create chat channels.
```json
{
  "prefix": "{rank}",
  "suffix": ""
}
```

## API

```
repositories {
    maven { url "https://repo.jorisg.com/snapshots" }
}

dependencies {
    implementation 'com.gufli.bricknametags:api:1.0-SNAPSHOT'
}
```

```java
NametagAPI.setNametag(player, Component.text("hey"), Component.text("oi"));
NametagAPI.clear(player);
```

Nametags wil automatically evaluate placeholders 10 times per second (async) and update if something changed.

## Credits

* The [Minestom](https://github.com/Minestom/Minestom) project

## Contributing

Check our [contributing info](CONTRIBUTING.md)

