package nf.fr.ephys.omnifluids.helper;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ChatHelper {
	public static void sendChatMessage(ICommandSender user, String message) {
		if (user.getEntityWorld() == null || !user.getEntityWorld().isRemote)
			user.addChatMessage(new ChatComponentText(message));
	}
}
