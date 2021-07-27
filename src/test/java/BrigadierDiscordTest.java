import io.github.jamalam360.BrigadierListener;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.LoginException;

import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

/**
 * @author Jamalam360
 */
public class BrigadierDiscordTest extends ListenerAdapter {
    private static final String TOKEN = System.getenv("BRIGADIER_TESTING_BOT_TOKEN");
    private static final CommandDispatcher<Message> COMMAND_DISPATCHER = new CommandDispatcher<>();
    private static final JDA jda = null;

    private static String lastInput = "";

    static {
        try {
            JDA = JDABuilder.createDefault(TOKEN).addEventListeners(new BrigadierListener(COMMAND_DISPATCHER)).build();
        } catch (LoginException e) {
            System.out.println("Failed to login. Ensure you have a bot token in your environment variables.");
            System.exit(0);
        }
    }

    @Test
    public static void registerCommands() {
        registerTestCommands();

        jda.getGuilds().get(0).getTextChannels().get(0).sendMessage("")
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

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
                    context.getSource().getChannel().sendMessage("Testing!").queue();
                    return 1;
                }));
    }

    private static LiteralArgumentBuilder<Message> literal(String literal) {
        return LiteralArgumentBuilder.literal(literal);
    }

    private static <T> RequiredArgumentBuilder<Message, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
