package net.minecraftforge.permissions.api;

import net.minecraftforge.common.FakePlayer;
import net.minecraftforge.permissions.api.context.IContext;
import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.permissions.opbasedimpl.OpPermFactory;

public final class PermissionsManager
{
    private PermissionsManager()
    {
        // no touch
    }

    private static       boolean            wasSet  = false;
    private static final PermBuilderFactory DEFAULT = new OpPermFactory(); // for now.
    private static PermBuilderFactory FACTORY;

    public static boolean checkPerm(EntityPlayer player, String node)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        IContext context = FACTORY.getDefaultContext(player);
        return FACTORY.builder(player.username, node)
                      .setUserContext(context)
                      .setTargetContext(context)
                      .check();
    }

    public static boolean checkPerm(EntityPlayer player, String node, Entity targetContext)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext))
                      .check();
    }

    public static boolean checkPerm(EntityPlayer player, String node, ILocation targetContext)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext))
                      .check();
    }

    public static PermBuilder getPerm(EntityPlayer player, String node)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        IContext context = FACTORY.getDefaultContext(player);
        return FACTORY.builder(player.username, node)
                      .setUserContext(context)
                      .setTargetContext(context);
    }

    public static PermBuilder getPerm(EntityPlayer player, String node, Entity targetContext)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext));
    }

    public static PermBuilder getPerm(EntityPlayer player, String node, ILocation targetContext)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext));
    }

    public static PermBuilder getPerm(String username, String node, TileEntity userContext)
    {
        return FACTORY.builder(username, node).setUserContext(FACTORY.getDefaultContext(userContext));
    }

    public static PermBuilder getPerm(String username, String node)
    {
        return FACTORY.builder(username, node);
    }

    public static PermBuilderFactory getPermFactory()
    {
        return FACTORY;
    }

    public static void setPermFactory(PermBuilderFactory factory) throws IllegalStateException
    {
        if (factory == null)
        {
            FACTORY = DEFAULT;
            wasSet = false;
        }
        else if (wasSet)
        {
            throw new IllegalStateException("Two Mods are trying to register permissions systems!");
        }
        else
        {
            FACTORY = factory;
            wasSet = true;
        }
    }
}
