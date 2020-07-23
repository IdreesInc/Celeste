# Celeste - Add shooting stars to your Minecraft server
Celeste is a simple plugin that adds shooting stars to your Minecraft server. Inspired by the shooting stars from Animal Crossing and the falling stars from Breath of the Wild, this plugin brings some celestial ambience to your Minecraft world. 
## Features
- Add shooting stars to the night sky, with configurable frequency
- Find rare falling stars that deliver precious materials in a sparkling package
- Watch meteor showers that occur every new moon, increasing the rate of shooting and falling stars
## Configuration
Installation is as simple as copying the newest build jar to your plugins folder. **A configuration file is not added by default**, but can be easily created by making a "Celeste" directory in your plugins folder and creating a "config.yml" within that directory. It is not necessary (nor recommended) to copy everything from the default config shown below, instead copy only the lines you wish to change.
### Defaults
``` yaml
# The number of shooting stars to create per minute for each world
shooting-stars-per-minute: 6
# Whether to create falling stars or not
falling-stars-enabled: true
# The number of falling stars to create per minute for each world
falling-stars-per-minute: 0.2
# The maximum distance around a player within which a falling star may spawn
falling-stars-radius: 75
# How many ticks a falling star should spark for once it has landed (20 ticks is a second)
falling-stars-spark-time: 200
# How many experience points (not levels) to provide from catching a falling star
falling-stars-experience: 100
# The possible loot from a falling star, and the weighted chance of each item appearing
falling-stars-loot:
  nether_star: 0.05
  diamond: 60
  emerald: 20
  fire_charge: 20
# Whether to have meteor showers on nights with a new moon, increasing the spawn rates
new-moon-meteor-shower: true
# The number of shooting stars to create per minute during meteor showers
shooting-stars-per-minute-during-meteor-showers: 15
# The number of falling stars to create per minute during meteor showers
falling-stars-per-minute-during-meteor-showers: 0.3
```
### Falling Star Loot
Falling stars drop loot wherever they fall, and spark for 10 seconds (200 ticks) by default to show their location. The loot they drop is randomly selected from the loot table in the config, with each material being given a weight. For instance, in the default config, there is a 60% chance for a diamond, 20% of an emerald, 20% chance of a fire_charge, and a 0.05% chance of a nether star (1 in 2000). Given the default probability for falling stars (0.2 a minute or about 2 a night), that means it would take about 1000 in game days to find a nether star, which is to say that it is basically an easter egg at that point. Experience also drops from falling stars, 100 points (not levels) by default.

To define your own loot tables, add the `falling-stars-loot` attribute to your config (remember, you will have to create the plugin config if you haven't already done so) and list each item you want as well as the probability for it to appear. **The names of the items must be from the list provided [here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)**. Remember to only add materials available in your server version.
