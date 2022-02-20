# BrickNametags

A nametag/tablist extension for [Minestom](https://github.com/Minestom/Minestom).

## Install

Get the [release](./releases) 
and place it in the extension folder of your minestom server.

### Dependencies
* [BrickPlaceholders](https://github.com/MinestomBrick/BrickPlaceholders)
* [BrickScheduler](https://github.com/MinestomBrick/BrickScheduler)


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

### Maven
```
repositories {
    maven { url "https://repo.jorisg.com/snapshots" }
}

dependencies {
    implementation 'org.minestombrick.nametags:api:1.0-SNAPSHOT'
}
```

### Usage

Check the [javadocs](https://minestombrick.github.io/BrickNametags/)

```java
NametagAPI.setNametag(player, Component.text("hey"), Component.text("oi"));
NametagAPI.clear(player);
```
