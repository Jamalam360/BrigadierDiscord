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
            JDABuilder.createDefault(TOKEN).addEventListeners(new BrigadierListener(COMMAND_DISPATCHER, "??")).build();
        } catch (Exception e) {
            System.out.println("Failed to login. Ensure you have a bot token in your environment variables.");
            System.exit(0);
        }
    }
}
