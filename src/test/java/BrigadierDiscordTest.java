/*
 * MIT License
 *
 * Copyright (c) 2021 Jamalam360
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.mojang.brigadier.CommandDispatcher;
import io.github.jamalam360.BrigadierListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;

import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static io.github.jamalam360.CommandManager.argument;
import static io.github.jamalam360.CommandManager.literal;

/**
 * @author Jamalam360
 */

public class BrigadierDiscordTest {
    private static final String TOKEN = System.getenv("BRIGADIER_TESTING_BOT_TOKEN");
    private static final CommandDispatcher<Message> COMMAND_DISPATCHER = new CommandDispatcher<>();

    public static void main(String[] args) {
        registerTestCommands();
    }

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

    static {
        try {
            BrigadierListener listener = new BrigadierListener(COMMAND_DISPATCHER, "??");
            listener.addListener(new TestEventListener());
            JDABuilder.createDefault(TOKEN).addEventListeners(listener).build();
        } catch (Exception e) {
            System.out.println("Failed to login. Ensure you have a bot token in your environment variables.");
            System.exit(0);
        }
    }
}
