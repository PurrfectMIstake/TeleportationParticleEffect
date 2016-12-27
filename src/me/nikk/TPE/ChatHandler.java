package me.nikk.TPE;

import net.md_5.bungee.api.ChatColor;

public class ChatHandler
{
  public static String color(String msg)
  {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }
}
