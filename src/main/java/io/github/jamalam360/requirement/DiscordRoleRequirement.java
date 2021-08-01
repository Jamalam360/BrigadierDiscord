package io.github.jamalam360.requirement;

import com.mojang.brigadier.context.CommandContext;
import net.dv8tion.jda.api.entities.Message;

/**
 * @author Jamalam360
 */
public class DiscordRoleRequirement {
    public static boolean hasRole(CommandContext<Message> context, long roleId) {
        return context.getSource().getGuild().getMembersWithRoles(context.getSource().getGuild().getRoleById(roleId)).contains(context.getSource().getGuild().getMember(context.getSource().getAuthor()));
    }

    public static boolean hasRole(CommandContext<Message> context, String roleId) {
        return context.getSource().getGuild().getMembersWithRoles(context.getSource().getGuild().getRoleById(roleId)).contains(context.getSource().getGuild().getMember(context.getSource().getAuthor()));
    }
}
