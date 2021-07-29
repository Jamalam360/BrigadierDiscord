import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.jamalam360.BrigadierDiscordEventListener;
import net.dv8tion.jda.api.entities.Message;

/**
 * @author Jamalam360
 */
public class TestEventListener implements BrigadierDiscordEventListener {
    @Override
    public String onMessageReceived(String input) {
        System.out.println("Message Received!");
        return BrigadierDiscordEventListener.super.onMessageReceived(input);
    }

    @Override
    public ParseResults<Message> onParseFinish(ParseResults<Message> results) {
        System.out.println("Parse Finished!");
        return BrigadierDiscordEventListener.super.onParseFinish(results);
    }

    @Override
    public void onExecutionFinish(int result) {
        System.out.println("Execution Finished (" + result + ")");
    }

    @Override
    public void onError(CommandSyntaxException e) {
        System.out.println("Error received!");
    }
}
