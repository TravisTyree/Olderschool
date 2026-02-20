package com.olderschool;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("example")
public interface OlderSchoolConfig extends Config
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

	@ConfigItem(
			keyName = "legacyScorpion",
			name = "Legacy Scorpion",
			description = "Replaces scorpions with the legacy model and animations"
	)
	default boolean legacyScorpion()
	{
		return true;
	}

	@ConfigItem(
			keyName = "legacyCaveBug",
			name = "Legacy Cave Bug",
			description = "Replaces cave bugs with the legacy model and animations"
	)
	default boolean legacyCaveBug()
	{
		return true;
	}

	// -------------------------------------------------------------------------
	// EXPERIMENTAL
	// -------------------------------------------------------------------------

	@ConfigSection(
			name = "Experimental",
			description = "Experimental features - may not work as expected",
			position = 99,
			closedByDefault = false
	)
	String experimentalSection = "experimental";

	@ConfigItem(
			keyName = "legacyGreenDragon",
			name = "Legacy Green Dragon",
			description = "Replaces green dragons with a legacy green dragon model and animations. Experimental.",
			section = experimentalSection
	)
	default boolean legacyGreenDragon()
	{
		return false;
	}
	@ConfigItem(
			keyName = "legacyKingBlackDragon",
			name = "Legacy King Black Dragon",
			description = "Replaces the King Black Dragon with the legacy King Black Dragon model and animations. Experimental.",
			section = experimentalSection
	)
	default boolean legacyKingBlackDragon()
	{
		return true;
	}
}