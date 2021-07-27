package io.github.jamalam360;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jamalam360
 */
public class BrigadierListener extends ListenerAdapter {
    private final CommandDispatcher<Message> commandDispatcher;
    private final String prefix;

    public BrigadierListener(CommandDispatcher<Message> dispatcher) {
        this.commandDispatcher = dispatcher;
        this.prefix = "";
    }

    public BrigadierListener(CommandDispatcher<Message> dispatcher, String prefix) {
        this.commandDispatcher = dispatcher;
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String input = event.getMessage().getContentRaw();

        if(input.indexOf(prefix) == 0) {
            StringBuilder sb = new StringBuilder(input);
            for (int i = 0; i < prefix.length(); i++) { //Remove prefix so brigadier can parse the command. Done in this way to avoid annoying regex issues
                sb.deleteCharAt(0);
            }

            try {
                if (!event.getAuthor().isBot()) {
                    ParseResults<Message> results = commandDispatcher.parse(sb.toString(), event.getMessage());
                    commandDispatcher.execute(results);
                }
            } catch (CommandSyntaxException ignored) {
            }
        }
    }
}
