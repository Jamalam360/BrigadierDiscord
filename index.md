# Welcome to BrigadierDiscord

BrigadierDiscord allows you to use [Mojangs Brigadier Commnad Parser](https://github.com/Mojang/brigadier) with discord bots.

## Downloading

BrigadierDiscord uses [jitpack](jitpack.io) to distribute files. To depend on it via gradle:

```gradle
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.Jamalam360:BrigadierDiscord:Tag'
}
```

Replace `Tag` with the version you want (you probably want `SNAPSHOT`), you can find the versions [here](https://jitpack.io/#Jamalam360/BrigadierDiscord)

## Using

This tutorial assumes you are already familiar with JDA and have a bot ready, you can learn how to do that on [the JDA Wiki](https://github.com/DV8FromTheWorld/JDA/wiki).

We will register our commands with a `CommandDispatcher`, so create one with `CommandDispatcher<Message> COMMAND_DISPATCHER = new CommandDispatcher<>();`. Next, add the event listener to your JDA with `<...>.addEventListeners(new BrigadierListener(COMMAND_DISPATCHER)).<...>`, if you want your commands to start with a prefix, you can specify it in the constructor of the `BrigadierListener` with `new BrigadierListener(COMMAND_DISPATCHER, "myPrefix")`
