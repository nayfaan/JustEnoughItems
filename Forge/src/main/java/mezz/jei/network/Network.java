package mezz.jei.network;

import mezz.jei.common.network.packets.PacketJei;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ICustomPacket;
import net.minecraftforge.network.NetworkDirection;
import org.apache.commons.lang3.tuple.Pair;

public class Network {
	public static void sendPacketToClient(PacketJei packet, ServerPlayer player) {
		Pair<FriendlyByteBuf, Integer> packetData = packet.getPacketData();
		ICustomPacket<Packet<?>> payload = NetworkDirection.PLAY_TO_CLIENT.buildPacket(packetData, PacketHandler.CHANNEL_ID);
		player.connection.send(payload.getThis());
	}
}
