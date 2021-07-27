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
