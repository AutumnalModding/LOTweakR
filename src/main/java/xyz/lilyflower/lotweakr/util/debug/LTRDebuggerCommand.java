package xyz.lilyflower.lotweakr.util.debug;

import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class LTRDebuggerCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "ltdebug";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Usage: /ltdebug <verb> [arguments]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;

            if (args.length >= 1) {
                switch (args[0]) {
                    case "dumpWaypoints":
                        sendWaypointDebugInfo(player);
                        break;

                    case "dumpInvasions":
                        for (LOTRInvasions invasion : LOTRInvasions.values()) {
                            player.addChatMessage(new ChatComponentText("Found invasion '" + invasion.name() + "' - faction " + invasion.invasionFaction + "."));
                        }
                        break;

                    case "invasionExists":
                        String invasion = (args[1] == null ? "thisInvasionNameDoesNotAndWillNotEverExistLmao" : args[1]);
                        player.addChatMessage(new ChatComponentText(Boolean.toString(LOTRInvasions.forName(invasion) != null)));
                        break;

                    case "help":
                    default:
                        player.addChatMessage(new ChatComponentText("Available LTDEBUG verbs: "));
                        player.addChatMessage(new ChatComponentText("    dumpWaypoints"));
                        player.addChatMessage(new ChatComponentText("    dumpInvasions"));
                        player.addChatMessage(new ChatComponentText("    invasionExists <INVASION>"));
                        break;
                }
            } else {
                player.addChatMessage(new ChatComponentText("Available LTDEBUG verbs: "));
                player.addChatMessage(new ChatComponentText("    dumpWaypoints"));
                player.addChatMessage(new ChatComponentText("    dumpInvasions"));
                player.addChatMessage(new ChatComponentText("    invasionExists <INVASION>"));
            }
        }
    }

    private void sendWaypointDebugInfo(EntityPlayer player) {
        for (LOTRWaypoint.Region region : LOTRWaypoint.Region.values()) {
            player.addChatMessage(new ChatComponentText("Listing region '" + region.name() + "' waypoint coordinates:"));

            region.waypoints.forEach(waypoint -> {
                player.addChatMessage(new ChatComponentText(("Waypoint '"
                        + waypoint.name()
                        + "' coordinates: "
                        + waypoint.getXCoord()
                        + " "
                        + waypoint.getZCoord()))
                );
            });
        }

        player.addChatMessage(new ChatComponentText(""));

    }
}
