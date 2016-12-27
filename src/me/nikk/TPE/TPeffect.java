package me.nikk.TPE;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sun.org.apache.xerces.internal.xs.StringList;

@SuppressWarnings("unused")
public class TPeffect
  extends JavaPlugin
  implements Listener
{
  public void loadConfiguration()
  {
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
  }
  
	public static TPeffect instance;
	
  public void onEnable()
  {
    File f = new File(getDataFolder(), "config.yml");
    saveDefaultConfig();
	instance = this;
    if (!f.exists()) {
      saveConfig();
      Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "TeleportationParticleEffect" + ChatColor.GREEN + " Has been enabled - v-" + getDescription().getVersion());
    }
    loadConfiguration();
    getServer().getPluginManager().registerEvents(this, this);
  }
  
  public void onDisable() {}
  
  class KPListener
    implements Listener
  {
    private final TPeffect plugin;
    
    public KPListener(TPeffect plugin)
    {
      this.plugin = plugin;
      this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
  }
  
  class PlayerListener1
    implements Listener
  {
    private final TPeffect plugin;
    
    public PlayerListener1(TPeffect plugin)
    {
      this.plugin = plugin;
      this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	    Player player = (Player)sender;
	    if (commandLabel.equalsIgnoreCase("tpe"))
	    {
	      if ((!player.hasPermission("TPE.reload") || !player.isOp()))
	    		  {
	    	  player.sendMessage(ChatHandler.color(this.getConfig().getString("No-Permission")));

	      }
	      else
	      {
	          	player.sendMessage(ChatHandler.color(this.getConfig().getString("Reload-Message")));
				reloadConfig();
	      }
	    }
		return false;
  }
  
  @EventHandler
  public void toggl(PlayerTeleportEvent event)
    throws InterruptedException
  {
    final Player player = event.getPlayer();
    boolean isEnabled = getConfig().getBoolean("Enable-Teleport-Message");
	if (isEnabled == true)
	{
    player.sendMessage(ChatHandler.color(getConfig().getString("Teleport-Message")));
    }else{
    	//do nothing
    	{
    if (player.hasPermission("TPE.effect"))
    {
        boolean isEnabled2 = getConfig().getBoolean("Enable-Teleport-Sound");
    	if (isEnabled2 == true)
    	{
            String ss = getConfig().getString("Teleport-Particle");
    player.playSound(player.getLocation(),Sound.BAT_TAKEOFF, (float)getConfig().getDouble("TeleportSoundVolume"), (float)getConfig().getDouble("TeleportSoundPitch"));
    	}else{
    		//do nothing
    	}
    new BukkitRunnable()
    {
      double phi = 0.0D;
      
      public void run()
      {
        this.phi += 0.3926990816987241D;
        
        Location location1 = player.getLocation();
        for (double t = 0.0D; t <= 6.283185307179586D; t += 0.1963495408493621D) {
          for (double i = 0.0D; i <= 1.0D; i += 1.0D)
          {
            double x = 0.4D * (6.283185307179586D - t) * 0.5D * Math.cos(t + this.phi + i * 3.141592653589793D);
            double y = 0.5D * t;
            double z = 0.4D * (6.283185307179586D - t) * 0.5D * Math.sin(t + this.phi + i * 3.141592653589793D);
            location1.add(x, y, z);
            
            String s = getConfig().getString("Teleport-Particle");
            ParticleEffect.valueOf(s).display(0.0F, 0.0F, 0.0F, 0.0F, 1, location1, TPeffect.this.getConfig().getInt("TeleportParticleVisibleRange"));
            location1.subtract(x, y, z);
          }
        }
        if (this.phi > TPeffect.this.getConfig().getInt("TeleportEffectDisplayTime") * 3.141592653589793D) {
          cancel();
        }
      }
    }.runTaskTimer(getPlugin(TPeffect.class), 0L, 3L);
    Thread.sleep(10L);
    }
    else
    {
    	//nothing
    }
  }
}
}
}