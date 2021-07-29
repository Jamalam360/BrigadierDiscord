package io.github.jamalam360;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dv8tion.jda.api.entities.Message;

/**
 * @author Jamalam360
 */
public interface BrigadierDiscordEventListener {
    default String onMessageReceived(String input) {
        return input;
    }

    default ParseResults<Message> onParseFinish(ParseResults<Message> results) {
        return results;
    }

    default void onExecutionFinish(int result) {

    }

    default void onError(CommandSyntaxException e) {

    }
}
