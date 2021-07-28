# Welcome to BrigadierDiscord

BrigadierDiscord allows you to use [Mojangs Brigadier Commnad Parser](https://github.com/Mojang/brigadier) with discord bots.

## Downloading

BrigadierDiscord uses [jitpack](jitpack.io) to distribute files. To depend on it via gradle:

```gradle
repositories {
  maven { url 'https://jitpack.io' }
  maven { url 'https://libraries.minecraft.net' }
}

dependencies {
  implementation 'com.github.Jamalam360:BrigadierDiscord:Tag'
  implementation 'com.mojang:brigadier:(the latest version)'
}
```

Replace `Tag` with the version you want, you can find the versions [here](https://jitpack.io/#Jamalam360/BrigadierDiscord). You can find brigadier versions [here](https://github.com/Mojang/brigadier)

## Using

This tutorial assumes you are already familiar with JDA and have a bot ready, you can learn how to do that on [the JDA Wiki](https://github.com/DV8FromTheWorld/JDA/wiki).

We will register our commands with a `CommandDispatcher`, so create one with `CommandDispatcher<Message> COMMAND_DISPATCHER = new CommandDispatcher<>();`.

Next, add the event listener to your JDA with `<...>.addEventListeners(new BrigadierListener(COMMAND_DISPATCHER)).<...>`, if you want your commands to start with a prefix, you can specify it in the constructor of the `BrigadierListener` with `new BrigadierListener(COMMAND_DISPATCHER, "myPrefix")`

Now you must register some commands with brigadier. Brigadiers [official GitHub](https://github.com/Mojang/brigadier) has a good explanation of how to do this but an example is provided below.

Please make sure you are using `literal` and `argument` from `CommandManager` and not anywhere else as you could run into issues (I recommend adding a static import for these as it gets messy quickly otherwise).

```java
import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static io.github.jamalam360.CommandManager.argument;
import static io.github.jamalam360.CommandManager.literal;

<...>

private static void registerTestCommands() {
        COMMAND_DISPATCHER.register(literal("jda")
                .then(literal("double")
                        .then(argument("number", doubleArg())
                                .executes(context -> {
                                    context.getSource().getChannel().sendMessage(String.valueOf(context.getArgument("number", Double.class) * 2)).queue();
                                    return 1;
                                })))
                .then(literal("echo")
                        .then(argument("input", greedyString())
                                .executes(context -> {
                                    context.getSource().getChannel().sendMessage("You said '" + context.getArgument("input", String.class) + "'").queue();
                                    return 1;
                                })))
                .executes(context -> {
                    context.getSource().getChannel().sendMessage("Test Message!").queue();
                    return 1;
                }));
    }
```

The above example creates three commands:

- `jda` sends a message saying 'Test Message!'
- `jda echo <a string>` sends a message saying 'You said '(a string)''
- `jda double <a number>` sends a message saying '(double a number)'
  
Note: I wouldn't recommend using lambdas as I have done here, it is clener to have each command be a class implementing `Command<Message>` and overriding the `run` method

The `context` parameter has many useful methods, but the most useful are `getArgument` and `getSource`. `getSource` returns a JDA message that you can use to send responses.
