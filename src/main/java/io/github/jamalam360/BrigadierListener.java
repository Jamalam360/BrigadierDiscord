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

    public BrigadierListener(CommandDispatcher<Message> dispatcher) {
        this.commandDispatcher = dispatcher;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String input = event.getMessage().getContentRaw();
        try {
            if (!event.getAuthor().isBot()) {
                ParseResults<Message> results = commandDispatcher.parse(input, event.getMessage());
                commandDispatcher.execute(results);
            }
        } catch (CommandSyntaxException ignored) {
        }
    }
}
