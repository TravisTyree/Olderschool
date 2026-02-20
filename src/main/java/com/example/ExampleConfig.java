package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface ExampleConfig extends Config
{
	@ConfigItem(
			keyName = "legacyGoblin",
			name = "Legacy Goblin",
			description = "Replaces goblins with the legacy model and animations"
	)
	default boolean legacyGoblin()
	{
		return true;
	}

	@ConfigItem(
			keyName = "legacyGreaterDemon",
			name = "Legacy Greater Demon",
			description = "Replaces greater demons with the legacy model and animations"
	)
	default boolean legacyGreaterDemon()
	{
		return true;
	}

	@ConfigItem(
			keyName = "legacyBlackDemon",
			name = "Legacy Black Demon",
			description = "Replaces black demons with the legacy model and animations"
	)
	default boolean legacyBlackDemon()
	{
		return true;
	}

	@ConfigItem(
			keyName = "legacyLesserDemon",
			name = "Legacy Lesser Demon",
			description = "Replaces lesser demons with the legacy model and animations"
	)
	default boolean legacyLesserDemon()
	{
		return true;
	}

	@ConfigItem(
			keyName = "legacyHillGiant",
			name = "Legacy Hill Giant",
			description = "Replaces hill giants with the legacy model and animations"
	)
	default boolean legacyHillGiant()
	{
		return true;
	}

	@ConfigItem(
			keyName = "legacyFireGiant",
			name = "Legacy Fire Giant",
			description = "Replaces fire giants with the legacy model and animations"
	)
	default boolean legacyFireGiant()
	{
		return true;
	}
	@ConfigItem(
			keyName = "legacyRedDragon",
			name = "Legacy Red Dragon",
			description = "Replaces red dragons with the legacy model and animations"
	)
	default boolean legacyRedDragon()
	{
		return true;
	}
	@ConfigItem(
			keyName = "legacyBlackDragon",
			name = "Legacy Black Dragon",
			description = "Replaces black dragons with the legacy model and animations"
	)
	default boolean legacyBlackDragon()
	{
		return true;
	}

	@ConfigItem(
			keyName = "legacyBlueDragon",
			name = "Legacy Blue Dragon",
			description = "Replaces blue dragons with the legacy model and animations"
	)
	default boolean legacyBlueDragon()
	{
		return true;
	}

	@ConfigItem(
			keyName = "legacyBabyBlueDragon",
			name = "Legacy Baby Blue Dragon",
			description = "Replaces baby blue dragons with the legacy model and animations"
	)
	default boolean legacyBabyBlueDragon()
	{
		return true;
	}
}
