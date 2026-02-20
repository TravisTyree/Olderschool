package com.olderschool;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ClientTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@PluginDescriptor(name = "OlderSchool")
public class OlderSchoolPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private OlderSchoolConfig config;

	// Goblin
	private static final Set<Integer> GOBLIN_SOURCE_IDS = Set.of(3029, 3030, 3031, 3032, 3033, 3034, 3035, 3036);
	private static final int GOBLIN_TARGET_ID = 15621;
	private static final int GOBLIN_ANIM_IDLE   = 311;
	private static final int GOBLIN_ANIM_WALK   = 308;
	private static final int GOBLIN_ANIM_ATTACK = 309;
	private static final int GOBLIN_ANIM_BLOCK  = 312;
	private static final int GOBLIN_ANIM_DEATH  = 313;
	private static final Set<Integer> GOBLIN_SRC_ATTACK = Set.of(6184, 6154, 6160, 6166, 6172, 6178);
	private static final Set<Integer> GOBLIN_SRC_BLOCK  = Set.of(6183, 6153, 6159, 6165, 6171, 6177);
	private static final Set<Integer> GOBLIN_SRC_DEATH  = Set.of(6182, 6152, 6158, 6164, 6170, 6176);

	// Greater Demon
	private static final Set<Integer> GREATER_DEMON_SOURCE_IDS = Set.of(7244, 7245, 7246, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 7871, 7872, 7873, 12387);
	private static final int GREATER_DEMON_TARGET_ID = 15607;
	private static final int GREATER_DEMON_ANIM_IDLE   = 13743;
	private static final int GREATER_DEMON_ANIM_WALK   = 13744;
	private static final int GREATER_DEMON_ANIM_ATTACK = 13745;
	private static final int GREATER_DEMON_ANIM_BLOCK  = 13743;
	private static final int GREATER_DEMON_ANIM_DEATH  = 13748;
	private static final Set<Integer> GREATER_DEMON_SRC_ATTACK = Set.of(64);
	private static final Set<Integer> GREATER_DEMON_SRC_BLOCK  = Set.of(65);
	private static final Set<Integer> GREATER_DEMON_SRC_DEATH  = Set.of(68);

	// Black Demon
	private static final Set<Integer> BLACK_DEMON_SOURCE_IDS = Set.of(240, 5874, 5875, 5876, 5877, 6295, 6357, 7242, 7243, 7874, 7875, 7876, 12385, 2048, 2049, 2050, 2051, 2052);
	private static final int BLACK_DEMON_TARGET_ID = 15608;
	private static final int BLACK_DEMON_ANIM_IDLE   = 13743;
	private static final int BLACK_DEMON_ANIM_WALK   = 13744;
	private static final int BLACK_DEMON_ANIM_ATTACK = 13745;
	private static final int BLACK_DEMON_ANIM_BLOCK  = 13746;
	private static final int BLACK_DEMON_ANIM_DEATH  = 13747;
	private static final Set<Integer> BLACK_DEMON_SRC_ATTACK = Set.of(64);
	private static final Set<Integer> BLACK_DEMON_SRC_BLOCK  = Set.of(65);
	private static final Set<Integer> BLACK_DEMON_SRC_DEATH  = Set.of(67);

	// Lesser Demon
	private static final Set<Integer> LESSER_DEMON_SOURCE_IDS = Set.of(2005, 2006, 2007, 2008, 2018, 3982, 7247, 7248, 7656, 7657, 7664, 7865, 7866, 7867);
	private static final int LESSER_DEMON_TARGET_ID = 15606;
	private static final int LESSER_DEMON_ANIM_IDLE   = 13743;
	private static final int LESSER_DEMON_ANIM_WALK   = 13744;
	private static final int LESSER_DEMON_ANIM_ATTACK = 13745;
	private static final int LESSER_DEMON_ANIM_BLOCK  = 13746;
	private static final int LESSER_DEMON_ANIM_DEATH  = 13747;
	private static final Set<Integer> LESSER_DEMON_SRC_ATTACK = Set.of(64);
	private static final Set<Integer> LESSER_DEMON_SRC_BLOCK  = Set.of(65);
	private static final Set<Integer> LESSER_DEMON_SRC_DEATH  = Set.of(67);

	// Hill Giant
	private static final Set<Integer> HILL_GIANT_SOURCE_IDS = Set.of(7261, 2098, 2099, 2100, 2101, 2102, 2103, 12848, 12849, 12850, 10374, 10375, 10376);
	private static final int HILL_GIANT_TARGET_ID = 15620;
	private static final int HILL_GIANT_ANIM_IDLE   = 130;
	private static final int HILL_GIANT_ANIM_WALK   = 127;
	private static final int HILL_GIANT_ANIM_ATTACK = 128;
	private static final int HILL_GIANT_ANIM_BLOCK  = 129;
	private static final int HILL_GIANT_ANIM_DEATH  = 131;
	private static final Set<Integer> HILL_GIANT_SRC_ATTACK = Set.of(4652);
	private static final Set<Integer> HILL_GIANT_SRC_BLOCK  = Set.of(4651);
	private static final Set<Integer> HILL_GIANT_SRC_DEATH  = Set.of(4653);

	// Fire Giant group 1
	private static final Set<Integer> FIRE_GIANT_GROUP1_IDS = Set.of(7251, 7252, 2081, 2082, 2077, 2076, 2080, 2079);
	private static final int FIRE_GIANT_G1_SRC_IDLE = 4663;
	private static final int FIRE_GIANT_G1_SRC_WALK = 4661;

	// Fire Giant group 2
	private static final Set<Integer> FIRE_GIANT_GROUP2_IDS = Set.of(2083, 2078, 2075);
	private static final int FIRE_GIANT_G2_SRC_IDLE = 4662;
	private static final int FIRE_GIANT_G2_SRC_WALK = 4660;

	// All fire giant source IDs combined
	private static final Set<Integer> FIRE_GIANT_SOURCE_IDS = Set.of(7251, 7252, 2081, 2082, 2077, 2076, 2080, 2079, 2083, 2078, 2075);
	private static final int FIRE_GIANT_TARGET_ID = 15619;
	private static final int FIRE_GIANT_ANIM_IDLE   = 130;
	private static final int FIRE_GIANT_ANIM_WALK   = 127;
	private static final int FIRE_GIANT_ANIM_ATTACK = 128;
	private static final int FIRE_GIANT_ANIM_BLOCK  = 129;
	private static final int FIRE_GIANT_ANIM_DEATH  = 131;
	private static final Set<Integer> FIRE_GIANT_SRC_ATTACK = Set.of(4667, 4666);
	private static final Set<Integer> FIRE_GIANT_SRC_BLOCK  = Set.of(4665, 4664);
	private static final Set<Integer> FIRE_GIANT_SRC_DEATH  = Set.of(4668);

	// Green Dragon
	private static final Set<Integer> GREEN_DRAGON_SOURCE_IDS = Set.of(260, 261, 262, 263, 264);
	private static final int GREEN_DRAGON_RECOLOR_FROM_1 = 61;
	private static final int GREEN_DRAGON_RECOLOR_FROM_2 = 41;
	private static final int GREEN_DRAGON_RECOLOR_TO_1   = 21940;
	private static final int GREEN_DRAGON_RECOLOR_TO_2   = 21940;
	private static final String GREEN_DRAGON_NAME        = "Green Dragon";

	// Shared dragon animations (red, black, blue)
	private static final int DRAGON_ANIM_IDLE     = 13720;
	private static final int DRAGON_ANIM_WALK     = 13721;
	private static final int DRAGON_ANIM_SCRATCH  = 13722;
	private static final int DRAGON_ANIM_BLOCK    = 13723;
	private static final int DRAGON_ANIM_HEADBUTT = 13724;
	private static final int DRAGON_ANIM_FIRE     = 13725;
	private static final int DRAGON_ANIM_DEATH    = 13729;
	private static final Set<Integer> DRAGON_SRC_SCRATCH  = Set.of(80);
	private static final Set<Integer> DRAGON_SRC_BLOCK    = Set.of(89);
	private static final Set<Integer> DRAGON_SRC_HEADBUTT = Set.of(91);
	private static final Set<Integer> DRAGON_SRC_FIRE     = Set.of(81);
	private static final Set<Integer> DRAGON_SRC_DEATH    = Set.of(92);

	// Red Dragon
	private static final Set<Integer> RED_DRAGON_SOURCE_IDS = Set.of(247, 248, 249, 250, 251);
	private static final int RED_DRAGON_TARGET_ID = 15602;

	// Black Dragon
	private static final Set<Integer> BLACK_DRAGON_SOURCE_IDS = Set.of(252, 253, 254, 255, 256);
	private static final int BLACK_DRAGON_TARGET_ID = 15601;

	// Blue Dragon
	private static final Set<Integer> BLUE_DRAGON_SOURCE_IDS = Set.of(265, 266, 267, 268, 269);
	private static final int BLUE_DRAGON_TARGET_ID = 15603;

	// Baby Blue Dragon
	private static final Set<Integer> BABY_BLUE_DRAGON_SOURCE_IDS = Set.of(241, 242, 243);
	private static final int BABY_BLUE_DRAGON_TARGET_ID = 15605;
	private static final int BABY_BLUE_DRAGON_ANIM_IDLE   = 13735;
	private static final int BABY_BLUE_DRAGON_ANIM_WALK   = 13736;
	private static final int BABY_BLUE_DRAGON_ANIM_ATTACK = 13740;
	private static final int BABY_BLUE_DRAGON_ANIM_BLOCK  = 13741;
	private static final int BABY_BLUE_DRAGON_ANIM_DEATH  = 13742;
	private static final Set<Integer> BABY_BLUE_DRAGON_SRC_ATTACK = Set.of(25);
	private static final Set<Integer> BABY_BLUE_DRAGON_SRC_BLOCK  = Set.of(26);
	private static final Set<Integer> BABY_BLUE_DRAGON_SRC_DEATH  = Set.of(28);

	// Cave Bug
	private static final Set<Integer> CAVE_BUG_SOURCE_IDS = Set.of(481, 483);
	private static final int CAVE_BUG_TARGET_ID = 15614;
	private static final int CAVE_BUG_ANIM_IDLE   = 1776;
	private static final int CAVE_BUG_ANIM_WALK   = 1774;
	private static final int CAVE_BUG_ANIM_ATTACK = 1777;
	private static final int CAVE_BUG_ANIM_BLOCK  = 1775;
	private static final int CAVE_BUG_ANIM_DEATH  = 1778;
	private static final Set<Integer> CAVE_BUG_SRC_ATTACK = Set.of(6079);
	private static final Set<Integer> CAVE_BUG_SRC_BLOCK  = Set.of(6080);
	private static final Set<Integer> CAVE_BUG_SRC_DEATH  = Set.of(6082);

	// Scorpion
	private static final Set<Integer> SCORPION_SOURCE_IDS = Set.of(2479, 2480, 3024, 13001, 13967);
	private static final int SCORPION_TARGET_ID = 15610;
	private static final int SCORPION_ANIM_IDLE   = 13749;
	private static final int SCORPION_ANIM_WALK   = 13750;
	private static final int SCORPION_ANIM_ATTACK = 13751;
	private static final int SCORPION_ANIM_BLOCK  = 13752;
	private static final int SCORPION_ANIM_DEATH  = 13753;
	private static final Set<Integer> SCORPION_SRC_ATTACK = Set.of(6254);
	private static final Set<Integer> SCORPION_SRC_BLOCK  = Set.of(6255);
	private static final Set<Integer> SCORPION_SRC_DEATH  = Set.of(6256);

	// King Black Dragon
	private static final Set<Integer> KING_BLACK_DRAGON_SOURCE_IDS = Set.of(239);
	private static final int KING_BLACK_DRAGON_TARGET_ID = 15604;
	private static final int KING_BLACK_DRAGON_ANIM_IDLE     = 13720;
	private static final int KING_BLACK_DRAGON_ANIM_WALK     = 13721;
	private static final int KING_BLACK_DRAGON_ANIM_SHOCK    = 13728;
	private static final int KING_BLACK_DRAGON_ANIM_ICE      = 13726;
	private static final int KING_BLACK_DRAGON_ANIM_POISON   = 13727;
	private static final int KING_BLACK_DRAGON_ANIM_BREATH   = 13728;
	private static final int KING_BLACK_DRAGON_ANIM_SCRATCH  = 13724;
	private static final int KING_BLACK_DRAGON_ANIM_HEADBUTT = 13722;
	private static final int KING_BLACK_DRAGON_ANIM_BLOCK    = 13723;
	private static final int KING_BLACK_DRAGON_ANIM_DEATH    = 13729;
	private static final Set<Integer> KING_BLACK_DRAGON_SRC_SHOCK   = Set.of(84);
	private static final Set<Integer> KING_BLACK_DRAGON_SRC_ICE     = Set.of(82);
	private static final Set<Integer> KING_BLACK_DRAGON_SRC_POISON  = Set.of(83);
	private static final Set<Integer> KING_BLACK_DRAGON_SRC_BREATH  = Set.of(81);
	private static final Set<Integer> KING_BLACK_DRAGON_SRC_SCRATCH  = Set.of(80);
	private static final Set<Integer> KING_BLACK_DRAGON_SRC_HEADBUTT = Set.of(91);
	private static final Set<Integer> KING_BLACK_DRAGON_SRC_BLOCK   = Set.of(4638);
	private static final Set<Integer> KING_BLACK_DRAGON_SRC_DEATH   = Set.of(92);

	private final Map<Integer, NPCComposition> originalComps = new HashMap<>();
	private final Map<Integer, int[]> originalAnims = new HashMap<>();

	private boolean goblinWasEnabled            = false;
	private boolean greaterDemonWasEnabled      = false;
	private boolean blackDemonWasEnabled        = false;
	private boolean lesserDemonWasEnabled       = false;
	private boolean hillGiantWasEnabled         = false;
	private boolean fireGiantWasEnabled         = false;
	private boolean redDragonWasEnabled         = false;
	private boolean blackDragonWasEnabled       = false;
	private boolean blueDragonWasEnabled        = false;
	private boolean babyBlueDragonWasEnabled    = false;
	private boolean scorpionWasEnabled          = false;
	private boolean caveBugWasEnabled           = false;
	private boolean greenDragonWasEnabled       = false;
	private boolean kingBlackDragonWasEnabled   = false;

	@Override
	protected void startUp() {
		goblinWasEnabled          = config.legacyGoblin();
		greaterDemonWasEnabled    = config.legacyGreaterDemon();
		blackDemonWasEnabled      = config.legacyBlackDemon();
		lesserDemonWasEnabled     = config.legacyLesserDemon();
		hillGiantWasEnabled       = config.legacyHillGiant();
		fireGiantWasEnabled       = config.legacyFireGiant();
		redDragonWasEnabled       = config.legacyRedDragon();
		blackDragonWasEnabled     = config.legacyBlackDragon();
		blueDragonWasEnabled      = config.legacyBlueDragon();
		babyBlueDragonWasEnabled  = config.legacyBabyBlueDragon();
		scorpionWasEnabled        = config.legacyScorpion();
		caveBugWasEnabled         = config.legacyCaveBug();
		greenDragonWasEnabled     = config.legacyGreenDragon();
		kingBlackDragonWasEnabled = config.legacyKingBlackDragon();
		log.info("OlderSchool started!");
	}

	@Override
	protected void shutDown() {
		revertAll();
		originalComps.clear();
		originalAnims.clear();
		log.info("OlderSchool stopped!");
	}

	@Subscribe
	public void onClientTick(ClientTick event) {
		boolean goblinEnabled          = config.legacyGoblin();
		boolean greaterDemonEnabled    = config.legacyGreaterDemon();
		boolean blackDemonEnabled      = config.legacyBlackDemon();
		boolean lesserDemonEnabled     = config.legacyLesserDemon();
		boolean hillGiantEnabled       = config.legacyHillGiant();
		boolean fireGiantEnabled       = config.legacyFireGiant();
		boolean redDragonEnabled       = config.legacyRedDragon();
		boolean blackDragonEnabled     = config.legacyBlackDragon();
		boolean blueDragonEnabled      = config.legacyBlueDragon();
		boolean babyBlueDragonEnabled  = config.legacyBabyBlueDragon();
		boolean scorpionEnabled        = config.legacyScorpion();
		boolean caveBugEnabled         = config.legacyCaveBug();
		boolean greenDragonEnabled     = config.legacyGreenDragon();
		boolean kingBlackDragonEnabled = config.legacyKingBlackDragon();

		if (goblinWasEnabled          && !goblinEnabled)          revertGroup(GOBLIN_SOURCE_IDS,            GOBLIN_TARGET_ID);
		if (greaterDemonWasEnabled    && !greaterDemonEnabled)    revertGroup(GREATER_DEMON_SOURCE_IDS,     GREATER_DEMON_TARGET_ID);
		if (blackDemonWasEnabled      && !blackDemonEnabled)      revertGroup(BLACK_DEMON_SOURCE_IDS,       BLACK_DEMON_TARGET_ID);
		if (lesserDemonWasEnabled     && !lesserDemonEnabled)     revertGroup(LESSER_DEMON_SOURCE_IDS,      LESSER_DEMON_TARGET_ID);
		if (hillGiantWasEnabled       && !hillGiantEnabled)       revertGroup(HILL_GIANT_SOURCE_IDS,        HILL_GIANT_TARGET_ID);
		if (fireGiantWasEnabled       && !fireGiantEnabled)       revertGroup(FIRE_GIANT_SOURCE_IDS,        FIRE_GIANT_TARGET_ID);
		if (redDragonWasEnabled       && !redDragonEnabled)       revertGroup(RED_DRAGON_SOURCE_IDS,        RED_DRAGON_TARGET_ID);
		if (blackDragonWasEnabled     && !blackDragonEnabled)     revertGroup(BLACK_DRAGON_SOURCE_IDS,      BLACK_DRAGON_TARGET_ID);
		if (blueDragonWasEnabled      && !blueDragonEnabled)      revertGroup(BLUE_DRAGON_SOURCE_IDS,       BLUE_DRAGON_TARGET_ID);
		if (babyBlueDragonWasEnabled  && !babyBlueDragonEnabled)  revertGroup(BABY_BLUE_DRAGON_SOURCE_IDS,  BABY_BLUE_DRAGON_TARGET_ID);
		if (scorpionWasEnabled        && !scorpionEnabled)        revertGroup(SCORPION_SOURCE_IDS,          SCORPION_TARGET_ID);
		if (caveBugWasEnabled         && !caveBugEnabled)         revertGroup(CAVE_BUG_SOURCE_IDS,          CAVE_BUG_TARGET_ID);
		if (greenDragonWasEnabled     && !greenDragonEnabled)     revertGroup(GREEN_DRAGON_SOURCE_IDS,      BLUE_DRAGON_TARGET_ID);
		if (kingBlackDragonWasEnabled && !kingBlackDragonEnabled) revertGroup(KING_BLACK_DRAGON_SOURCE_IDS, KING_BLACK_DRAGON_TARGET_ID);

		goblinWasEnabled          = goblinEnabled;
		greaterDemonWasEnabled    = greaterDemonEnabled;
		blackDemonWasEnabled      = blackDemonEnabled;
		lesserDemonWasEnabled     = lesserDemonEnabled;
		hillGiantWasEnabled       = hillGiantEnabled;
		fireGiantWasEnabled       = fireGiantEnabled;
		redDragonWasEnabled       = redDragonEnabled;
		blackDragonWasEnabled     = blackDragonEnabled;
		blueDragonWasEnabled      = blueDragonEnabled;
		babyBlueDragonWasEnabled  = babyBlueDragonEnabled;
		scorpionWasEnabled        = scorpionEnabled;
		caveBugWasEnabled         = caveBugEnabled;
		greenDragonWasEnabled     = greenDragonEnabled;
		kingBlackDragonWasEnabled = kingBlackDragonEnabled;

		if (goblinEnabled)          processGroup(GOBLIN_SOURCE_IDS,           GOBLIN_TARGET_ID,           GOBLIN_ANIM_IDLE,            GOBLIN_ANIM_WALK,            GOBLIN_SRC_ATTACK,            GOBLIN_SRC_BLOCK,            GOBLIN_SRC_DEATH,            GOBLIN_ANIM_ATTACK,            GOBLIN_ANIM_BLOCK,            GOBLIN_ANIM_DEATH);
		if (greaterDemonEnabled)    processGroup(GREATER_DEMON_SOURCE_IDS,    GREATER_DEMON_TARGET_ID,    GREATER_DEMON_ANIM_IDLE,     GREATER_DEMON_ANIM_WALK,     GREATER_DEMON_SRC_ATTACK,     GREATER_DEMON_SRC_BLOCK,     GREATER_DEMON_SRC_DEATH,     GREATER_DEMON_ANIM_ATTACK,     GREATER_DEMON_ANIM_BLOCK,     GREATER_DEMON_ANIM_DEATH);
		if (blackDemonEnabled)      processGroup(BLACK_DEMON_SOURCE_IDS,      BLACK_DEMON_TARGET_ID,      BLACK_DEMON_ANIM_IDLE,       BLACK_DEMON_ANIM_WALK,       BLACK_DEMON_SRC_ATTACK,       BLACK_DEMON_SRC_BLOCK,       BLACK_DEMON_SRC_DEATH,       BLACK_DEMON_ANIM_ATTACK,       BLACK_DEMON_ANIM_BLOCK,       BLACK_DEMON_ANIM_DEATH);
		if (lesserDemonEnabled)     processGroup(LESSER_DEMON_SOURCE_IDS,     LESSER_DEMON_TARGET_ID,     LESSER_DEMON_ANIM_IDLE,      LESSER_DEMON_ANIM_WALK,      LESSER_DEMON_SRC_ATTACK,      LESSER_DEMON_SRC_BLOCK,      LESSER_DEMON_SRC_DEATH,      LESSER_DEMON_ANIM_ATTACK,      LESSER_DEMON_ANIM_BLOCK,      LESSER_DEMON_ANIM_DEATH);
		if (hillGiantEnabled)       processGroup(HILL_GIANT_SOURCE_IDS,       HILL_GIANT_TARGET_ID,       HILL_GIANT_ANIM_IDLE,        HILL_GIANT_ANIM_WALK,        HILL_GIANT_SRC_ATTACK,        HILL_GIANT_SRC_BLOCK,        HILL_GIANT_SRC_DEATH,        HILL_GIANT_ANIM_ATTACK,        HILL_GIANT_ANIM_BLOCK,        HILL_GIANT_ANIM_DEATH);
		if (fireGiantEnabled)       processFireGiants();
		if (redDragonEnabled)       processDragonGroup(RED_DRAGON_SOURCE_IDS,   RED_DRAGON_TARGET_ID);
		if (blackDragonEnabled)     processDragonGroup(BLACK_DRAGON_SOURCE_IDS, BLACK_DRAGON_TARGET_ID);
		if (blueDragonEnabled)      processDragonGroup(BLUE_DRAGON_SOURCE_IDS,  BLUE_DRAGON_TARGET_ID);
		if (babyBlueDragonEnabled)  processGroup(BABY_BLUE_DRAGON_SOURCE_IDS, BABY_BLUE_DRAGON_TARGET_ID, BABY_BLUE_DRAGON_ANIM_IDLE, BABY_BLUE_DRAGON_ANIM_WALK, BABY_BLUE_DRAGON_SRC_ATTACK, BABY_BLUE_DRAGON_SRC_BLOCK, BABY_BLUE_DRAGON_SRC_DEATH, BABY_BLUE_DRAGON_ANIM_ATTACK, BABY_BLUE_DRAGON_ANIM_BLOCK, BABY_BLUE_DRAGON_ANIM_DEATH);
		if (scorpionEnabled)        processGroup(SCORPION_SOURCE_IDS,         SCORPION_TARGET_ID,         SCORPION_ANIM_IDLE,          SCORPION_ANIM_WALK,          SCORPION_SRC_ATTACK,          SCORPION_SRC_BLOCK,          SCORPION_SRC_DEATH,          SCORPION_ANIM_ATTACK,          SCORPION_ANIM_BLOCK,          SCORPION_ANIM_DEATH);
		if (caveBugEnabled)         processGroup(CAVE_BUG_SOURCE_IDS,         CAVE_BUG_TARGET_ID,         CAVE_BUG_ANIM_IDLE,          CAVE_BUG_ANIM_WALK,          CAVE_BUG_SRC_ATTACK,          CAVE_BUG_SRC_BLOCK,          CAVE_BUG_SRC_DEATH,          CAVE_BUG_ANIM_ATTACK,          CAVE_BUG_ANIM_BLOCK,          CAVE_BUG_ANIM_DEATH);
		if (greenDragonEnabled)     processGreenDragons();
		if (kingBlackDragonEnabled) processKingBlackDragon();
	}

	private void processDragonGroup(Set<Integer> sourceIds, int targetId) {
		NPCComposition targetComp = getTargetComp(targetId);
		if (targetComp == null) return;

		for (NPC npc : client.getNpcs()) {
			int id = npc.getId();
			if (!sourceIds.contains(id) && id != targetId) continue;

			int key = System.identityHashCode(npc);
			originalAnims.putIfAbsent(key, new int[]{npc.getIdlePoseAnimation(), npc.getWalkAnimation()});

			NPCComposition currentComp = npc.getTransformedComposition();
			if (currentComp != null && currentComp != targetComp) {
				originalComps.putIfAbsent(key, currentComp);
				Field compField = findFieldByValue(npc, currentComp);
				if (compField != null) {
					try {
						compField.set(npc, targetComp);
					} catch (Exception e) {
						log.warn("Comp swap failed: {}", e.getMessage());
					}
				}
			}

			npc.setIdlePoseAnimation(DRAGON_ANIM_IDLE);
			npc.setWalkAnimation(DRAGON_ANIM_WALK);
			npc.setWalkRotateLeft(DRAGON_ANIM_WALK);
			npc.setWalkRotateRight(DRAGON_ANIM_WALK);
			npc.setWalkRotate180(DRAGON_ANIM_WALK);
			npc.setRunAnimation(DRAGON_ANIM_WALK);
			npc.setIdleRotateLeft(DRAGON_ANIM_IDLE);
			npc.setIdleRotateRight(DRAGON_ANIM_IDLE);

			int anim = npc.getAnimation();
			if (DRAGON_SRC_SCRATCH.contains(anim))       npc.setAnimation(DRAGON_ANIM_SCRATCH);
			else if (DRAGON_SRC_BLOCK.contains(anim))    npc.setAnimation(DRAGON_ANIM_BLOCK);
			else if (DRAGON_SRC_HEADBUTT.contains(anim)) npc.setAnimation(DRAGON_ANIM_HEADBUTT);
			else if (DRAGON_SRC_FIRE.contains(anim))     npc.setAnimation(DRAGON_ANIM_FIRE);
			else if (DRAGON_SRC_DEATH.contains(anim))    npc.setAnimation(DRAGON_ANIM_DEATH);
		}
	}

	private void processKingBlackDragon() {
		NPCComposition targetComp = getTargetComp(KING_BLACK_DRAGON_TARGET_ID);
		if (targetComp == null) return;

		for (NPC npc : client.getNpcs()) {
			int id = npc.getId();
			if (!KING_BLACK_DRAGON_SOURCE_IDS.contains(id) && id != KING_BLACK_DRAGON_TARGET_ID) continue;

			int key = System.identityHashCode(npc);
			originalAnims.putIfAbsent(key, new int[]{npc.getIdlePoseAnimation(), npc.getWalkAnimation()});

			NPCComposition currentComp = npc.getTransformedComposition();
			if (currentComp != null && currentComp != targetComp) {
				originalComps.putIfAbsent(key, currentComp);
				Field compField = findFieldByValue(npc, currentComp);
				if (compField != null) {
					try {
						compField.set(npc, targetComp);
					} catch (Exception e) {
						log.warn("Comp swap failed: {}", e.getMessage());
					}
				}
			}

			npc.setIdlePoseAnimation(KING_BLACK_DRAGON_ANIM_IDLE);
			npc.setWalkAnimation(KING_BLACK_DRAGON_ANIM_WALK);
			npc.setWalkRotateLeft(KING_BLACK_DRAGON_ANIM_WALK);
			npc.setWalkRotateRight(KING_BLACK_DRAGON_ANIM_WALK);
			npc.setWalkRotate180(KING_BLACK_DRAGON_ANIM_WALK);
			npc.setRunAnimation(KING_BLACK_DRAGON_ANIM_WALK);
			npc.setIdleRotateLeft(KING_BLACK_DRAGON_ANIM_IDLE);
			npc.setIdleRotateRight(KING_BLACK_DRAGON_ANIM_IDLE);

			int anim = npc.getAnimation();
			if (KING_BLACK_DRAGON_SRC_SHOCK.contains(anim))         npc.setAnimation(KING_BLACK_DRAGON_ANIM_SHOCK);
			else if (KING_BLACK_DRAGON_SRC_ICE.contains(anim))      npc.setAnimation(KING_BLACK_DRAGON_ANIM_ICE);
			else if (KING_BLACK_DRAGON_SRC_POISON.contains(anim))   npc.setAnimation(KING_BLACK_DRAGON_ANIM_POISON);
			else if (KING_BLACK_DRAGON_SRC_BREATH.contains(anim))   npc.setAnimation(KING_BLACK_DRAGON_ANIM_BREATH);
			else if (KING_BLACK_DRAGON_SRC_SCRATCH.contains(anim))  npc.setAnimation(KING_BLACK_DRAGON_ANIM_SCRATCH);
			else if (KING_BLACK_DRAGON_SRC_HEADBUTT.contains(anim)) npc.setAnimation(KING_BLACK_DRAGON_ANIM_HEADBUTT);
			else if (KING_BLACK_DRAGON_SRC_BLOCK.contains(anim))    npc.setAnimation(KING_BLACK_DRAGON_ANIM_BLOCK);
			else if (KING_BLACK_DRAGON_SRC_DEATH.contains(anim))    npc.setAnimation(KING_BLACK_DRAGON_ANIM_DEATH);
		}
	}

	private void processGreenDragons() {
		NPCComposition blueLegacyComp = getTargetComp(BLUE_DRAGON_TARGET_ID);
		if (blueLegacyComp == null) return;

		for (NPC npc : client.getNpcs()) {
			int id = npc.getId();
			if (!GREEN_DRAGON_SOURCE_IDS.contains(id)) continue;

			int key = System.identityHashCode(npc);
			originalAnims.putIfAbsent(key, new int[]{npc.getIdlePoseAnimation(), npc.getWalkAnimation()});

			NPCComposition currentComp = npc.getTransformedComposition();
			if (currentComp != null && currentComp != blueLegacyComp) {
				originalComps.putIfAbsent(key, currentComp);

				NPCComposition greenComp = cloneAndRecolorComp(blueLegacyComp,
						new int[]{GREEN_DRAGON_RECOLOR_FROM_1, GREEN_DRAGON_RECOLOR_FROM_2},
						new int[]{GREEN_DRAGON_RECOLOR_TO_1,   GREEN_DRAGON_RECOLOR_TO_2},
						GREEN_DRAGON_NAME);

				if (greenComp != null) {
					Field compField = findFieldByValue(npc, currentComp);
					if (compField != null) {
						try {
							compField.set(npc, greenComp);
						} catch (Exception e) {
							log.warn("Green dragon comp swap failed: {}", e.getMessage());
						}
					}
				}
			}

			npc.setIdlePoseAnimation(DRAGON_ANIM_IDLE);
			npc.setWalkAnimation(DRAGON_ANIM_WALK);
			npc.setWalkRotateLeft(DRAGON_ANIM_WALK);
			npc.setWalkRotateRight(DRAGON_ANIM_WALK);
			npc.setWalkRotate180(DRAGON_ANIM_WALK);
			npc.setRunAnimation(DRAGON_ANIM_WALK);
			npc.setIdleRotateLeft(DRAGON_ANIM_IDLE);
			npc.setIdleRotateRight(DRAGON_ANIM_IDLE);

			int anim = npc.getAnimation();
			if (DRAGON_SRC_SCRATCH.contains(anim))       npc.setAnimation(DRAGON_ANIM_SCRATCH);
			else if (DRAGON_SRC_BLOCK.contains(anim))    npc.setAnimation(DRAGON_ANIM_BLOCK);
			else if (DRAGON_SRC_HEADBUTT.contains(anim)) npc.setAnimation(DRAGON_ANIM_HEADBUTT);
			else if (DRAGON_SRC_FIRE.contains(anim))     npc.setAnimation(DRAGON_ANIM_FIRE);
			else if (DRAGON_SRC_DEATH.contains(anim))    npc.setAnimation(DRAGON_ANIM_DEATH);
		}
	}

	private NPCComposition cloneAndRecolorComp(NPCComposition source, int[] recolorFrom, int[] recolorTo, String name) {
		try {
			NPCComposition clone = null;
			for (java.lang.reflect.Constructor<?> ctor : source.getClass().getDeclaredConstructors()) {
				ctor.setAccessible(true);
				if (ctor.getParameterCount() == 0) {
					clone = (NPCComposition) ctor.newInstance();
					break;
				}
			}
			if (clone == null) {
				log.warn("cloneAndRecolorComp: no no-arg constructor found");
				return null;
			}

			Class<?> clazz = source.getClass();
			while (clazz != null && clazz != Object.class) {
				for (Field f : clazz.getDeclaredFields()) {
					f.setAccessible(true);
					try { f.set(clone, f.get(source)); } catch (Exception ignored) {}
				}
				clazz = clazz.getSuperclass();
			}

			short[] foundFind    = null;
			short[] foundReplace = null;
			clazz = clone.getClass();
			java.util.List<Field> shortArrayFields = new java.util.ArrayList<>();
			while (clazz != null && clazz != Object.class) {
				for (Field f : clazz.getDeclaredFields()) {
					if (f.getType() == short[].class) {
						f.setAccessible(true);
						shortArrayFields.add(f);
					}
				}
				clazz = clazz.getSuperclass();
			}
			for (int fi = 0; fi < shortArrayFields.size(); fi++) {
				try {
					short[] candidate = (short[]) shortArrayFields.get(fi).get(clone);
					if (candidate == null) continue;
					boolean matches = false;
					for (short s : candidate) {
						for (int rf : recolorFrom) {
							if (s == (short) rf) { matches = true; break; }
						}
						if (matches) break;
					}
					if (matches && fi + 1 < shortArrayFields.size()) {
						foundFind    = candidate;
						foundReplace = (short[]) shortArrayFields.get(fi + 1).get(clone);
						break;
					}
				} catch (Exception ignored) {}
			}
			if (foundFind != null && foundReplace != null) {
				for (int i = 0; i < foundFind.length; i++) {
					for (int j = 0; j < recolorFrom.length; j++) {
						if (foundFind[i] == (short) recolorFrom[j]) {
							foundReplace[i] = (short) recolorTo[j];
						}
					}
				}
			} else {
				log.warn("cloneAndRecolorComp: could not locate recolor arrays");
			}

			clazz = clone.getClass();
			outer:
			while (clazz != null && clazz != Object.class) {
				for (Field f : clazz.getDeclaredFields()) {
					if (f.getType() != String.class) continue;
					f.setAccessible(true);
					try {
						Object val = f.get(clone);
						if (val instanceof String && !((String) val).isEmpty()) {
							f.set(clone, name);
							break outer;
						}
					} catch (Exception ignored) {}
				}
				clazz = clazz.getSuperclass();
			}

			return clone;
		} catch (Exception e) {
			log.warn("cloneAndRecolorComp failed: {}", e.getMessage());
			return null;
		}
	}

	private void processFireGiants() {
		NPCComposition targetComp = getTargetComp(FIRE_GIANT_TARGET_ID);
		if (targetComp == null) return;

		for (NPC npc : client.getNpcs()) {
			int id = npc.getId();
			if (!FIRE_GIANT_SOURCE_IDS.contains(id) && id != FIRE_GIANT_TARGET_ID) continue;

			int key = System.identityHashCode(npc);

			if (!originalAnims.containsKey(key)) {
				if (FIRE_GIANT_GROUP1_IDS.contains(id)) {
					originalAnims.put(key, new int[]{FIRE_GIANT_G1_SRC_IDLE, FIRE_GIANT_G1_SRC_WALK});
				} else if (FIRE_GIANT_GROUP2_IDS.contains(id)) {
					originalAnims.put(key, new int[]{FIRE_GIANT_G2_SRC_IDLE, FIRE_GIANT_G2_SRC_WALK});
				}
			}

			NPCComposition currentComp = npc.getTransformedComposition();
			if (currentComp != null && currentComp != targetComp) {
				originalComps.putIfAbsent(key, currentComp);
				Field compField = findFieldByValue(npc, currentComp);
				if (compField != null) {
					try {
						compField.set(npc, targetComp);
					} catch (Exception e) {
						log.warn("Comp swap failed: {}", e.getMessage());
					}
				}
			}

			npc.setIdlePoseAnimation(FIRE_GIANT_ANIM_IDLE);
			npc.setWalkAnimation(FIRE_GIANT_ANIM_WALK);
			npc.setWalkRotateLeft(FIRE_GIANT_ANIM_WALK);
			npc.setWalkRotateRight(FIRE_GIANT_ANIM_WALK);
			npc.setWalkRotate180(FIRE_GIANT_ANIM_WALK);
			npc.setRunAnimation(FIRE_GIANT_ANIM_WALK);
			npc.setIdleRotateLeft(FIRE_GIANT_ANIM_IDLE);
			npc.setIdleRotateRight(FIRE_GIANT_ANIM_IDLE);

			int anim = npc.getAnimation();
			if (FIRE_GIANT_SRC_ATTACK.contains(anim))     npc.setAnimation(FIRE_GIANT_ANIM_ATTACK);
			else if (FIRE_GIANT_SRC_BLOCK.contains(anim)) npc.setAnimation(FIRE_GIANT_ANIM_BLOCK);
			else if (FIRE_GIANT_SRC_DEATH.contains(anim)) npc.setAnimation(FIRE_GIANT_ANIM_DEATH);
		}
	}

	private NPCComposition getTargetComp(int targetId) {
		for (NPC npc : client.getNpcs()) {
			if (npc.getId() == targetId) return npc.getTransformedComposition();
		}
		return client.getNpcDefinition(targetId);
	}

	private void processGroup(Set<Integer> sourceIds, int targetId,
							  int idleAnim, int walkAnim,
							  Set<Integer> srcAttack, Set<Integer> srcBlock, Set<Integer> srcDeath,
							  int attackAnim, int blockAnim, int deathAnim) {
		NPCComposition targetComp = getTargetComp(targetId);
		if (targetComp == null) return;

		for (NPC npc : client.getNpcs()) {
			if (!sourceIds.contains(npc.getId()) && npc.getId() != targetId) continue;

			int key = System.identityHashCode(npc);
			originalAnims.putIfAbsent(key, new int[]{npc.getIdlePoseAnimation(), npc.getWalkAnimation()});

			NPCComposition currentComp = npc.getTransformedComposition();
			if (currentComp != null && currentComp != targetComp) {
				originalComps.putIfAbsent(key, currentComp);
				Field compField = findFieldByValue(npc, currentComp);
				if (compField != null) {
					try {
						compField.set(npc, targetComp);
					} catch (Exception e) {
						log.warn("Comp swap failed: {}", e.getMessage());
					}
				}
			}

			npc.setIdlePoseAnimation(idleAnim);
			npc.setWalkAnimation(walkAnim);
			npc.setWalkRotateLeft(walkAnim);
			npc.setWalkRotateRight(walkAnim);
			npc.setWalkRotate180(walkAnim);
			npc.setRunAnimation(walkAnim);
			npc.setIdleRotateLeft(idleAnim);
			npc.setIdleRotateRight(idleAnim);

			int anim = npc.getAnimation();
			if (srcAttack.contains(anim))     npc.setAnimation(attackAnim);
			else if (srcBlock.contains(anim)) npc.setAnimation(blockAnim);
			else if (srcDeath.contains(anim)) npc.setAnimation(deathAnim);
		}
	}

	private void revertGroup(Set<Integer> sourceIds, int targetId) {
		for (NPC npc : client.getNpcs()) {
			if (!sourceIds.contains(npc.getId()) && npc.getId() != targetId) continue;

			int key = System.identityHashCode(npc);

			NPCComposition original = originalComps.remove(key);
			if (original != null) {
				Field compField = findFieldByValue(npc, npc.getTransformedComposition());
				if (compField != null) {
					try {
						compField.set(npc, original);
					} catch (Exception e) {
						log.warn("Revert failed: {}", e.getMessage());
					}
				}
			}

			int[] anims = originalAnims.remove(key);
			if (anims != null) {
				npc.setIdlePoseAnimation(anims[0]);
				npc.setWalkAnimation(anims[1]);
				npc.setWalkRotateLeft(anims[1]);
				npc.setWalkRotateRight(anims[1]);
				npc.setWalkRotate180(anims[1]);
				npc.setRunAnimation(anims[1]);
				npc.setIdleRotateLeft(anims[0]);
				npc.setIdleRotateRight(anims[0]);
			}
		}
	}

	private void revertAll() {
		revertGroup(GOBLIN_SOURCE_IDS,            GOBLIN_TARGET_ID);
		revertGroup(GREATER_DEMON_SOURCE_IDS,     GREATER_DEMON_TARGET_ID);
		revertGroup(BLACK_DEMON_SOURCE_IDS,       BLACK_DEMON_TARGET_ID);
		revertGroup(LESSER_DEMON_SOURCE_IDS,      LESSER_DEMON_TARGET_ID);
		revertGroup(HILL_GIANT_SOURCE_IDS,        HILL_GIANT_TARGET_ID);
		revertGroup(FIRE_GIANT_SOURCE_IDS,        FIRE_GIANT_TARGET_ID);
		revertGroup(RED_DRAGON_SOURCE_IDS,        RED_DRAGON_TARGET_ID);
		revertGroup(BLACK_DRAGON_SOURCE_IDS,      BLACK_DRAGON_TARGET_ID);
		revertGroup(BLUE_DRAGON_SOURCE_IDS,       BLUE_DRAGON_TARGET_ID);
		revertGroup(BABY_BLUE_DRAGON_SOURCE_IDS,  BABY_BLUE_DRAGON_TARGET_ID);
		revertGroup(SCORPION_SOURCE_IDS,          SCORPION_TARGET_ID);
		revertGroup(CAVE_BUG_SOURCE_IDS,          CAVE_BUG_TARGET_ID);
		revertGroup(GREEN_DRAGON_SOURCE_IDS,      BLUE_DRAGON_TARGET_ID);
		revertGroup(KING_BLACK_DRAGON_SOURCE_IDS, KING_BLACK_DRAGON_TARGET_ID);
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event) {
		Actor actor = event.getActor();
		if (!(actor instanceof NPC)) return;
		NPC npc = (NPC) actor;
		int id = npc.getId();
		int current = npc.getAnimation();

		if (config.legacyGoblin() && (GOBLIN_SOURCE_IDS.contains(id) || id == GOBLIN_TARGET_ID)) {
			if (GOBLIN_SRC_ATTACK.contains(current))     { npc.setAnimation(GOBLIN_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (GOBLIN_SRC_BLOCK.contains(current)) { npc.setAnimation(GOBLIN_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (GOBLIN_SRC_DEATH.contains(current)) { npc.setAnimation(GOBLIN_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyGreaterDemon() && (GREATER_DEMON_SOURCE_IDS.contains(id) || id == GREATER_DEMON_TARGET_ID)) {
			if (GREATER_DEMON_SRC_ATTACK.contains(current))     { npc.setAnimation(GREATER_DEMON_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (GREATER_DEMON_SRC_BLOCK.contains(current)) { npc.setAnimation(GREATER_DEMON_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (GREATER_DEMON_SRC_DEATH.contains(current)) { npc.setAnimation(GREATER_DEMON_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyBlackDemon() && (BLACK_DEMON_SOURCE_IDS.contains(id) || id == BLACK_DEMON_TARGET_ID)) {
			if (BLACK_DEMON_SRC_ATTACK.contains(current))     { npc.setAnimation(BLACK_DEMON_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (BLACK_DEMON_SRC_BLOCK.contains(current)) { npc.setAnimation(BLACK_DEMON_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (BLACK_DEMON_SRC_DEATH.contains(current)) { npc.setAnimation(BLACK_DEMON_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyLesserDemon() && (LESSER_DEMON_SOURCE_IDS.contains(id) || id == LESSER_DEMON_TARGET_ID)) {
			if (LESSER_DEMON_SRC_ATTACK.contains(current))     { npc.setAnimation(LESSER_DEMON_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (LESSER_DEMON_SRC_BLOCK.contains(current)) { npc.setAnimation(LESSER_DEMON_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (LESSER_DEMON_SRC_DEATH.contains(current)) { npc.setAnimation(LESSER_DEMON_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyHillGiant() && (HILL_GIANT_SOURCE_IDS.contains(id) || id == HILL_GIANT_TARGET_ID)) {
			if (HILL_GIANT_SRC_ATTACK.contains(current))     { npc.setAnimation(HILL_GIANT_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (HILL_GIANT_SRC_BLOCK.contains(current)) { npc.setAnimation(HILL_GIANT_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (HILL_GIANT_SRC_DEATH.contains(current)) { npc.setAnimation(HILL_GIANT_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyFireGiant() && (FIRE_GIANT_SOURCE_IDS.contains(id) || id == FIRE_GIANT_TARGET_ID)) {
			if (FIRE_GIANT_SRC_ATTACK.contains(current))     { npc.setAnimation(FIRE_GIANT_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (FIRE_GIANT_SRC_BLOCK.contains(current)) { npc.setAnimation(FIRE_GIANT_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (FIRE_GIANT_SRC_DEATH.contains(current)) { npc.setAnimation(FIRE_GIANT_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyRedDragon() && (RED_DRAGON_SOURCE_IDS.contains(id) || id == RED_DRAGON_TARGET_ID)) {
			if (DRAGON_SRC_SCRATCH.contains(current))        { npc.setAnimation(DRAGON_ANIM_SCRATCH);  npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_BLOCK.contains(current))     { npc.setAnimation(DRAGON_ANIM_BLOCK);    npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_HEADBUTT.contains(current))  { npc.setAnimation(DRAGON_ANIM_HEADBUTT); npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_FIRE.contains(current))      { npc.setAnimation(DRAGON_ANIM_FIRE);     npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_DEATH.contains(current))     { npc.setAnimation(DRAGON_ANIM_DEATH);    npc.setAnimationFrame(0); }
		}
		if (config.legacyBlackDragon() && (BLACK_DRAGON_SOURCE_IDS.contains(id) || id == BLACK_DRAGON_TARGET_ID)) {
			if (DRAGON_SRC_SCRATCH.contains(current))        { npc.setAnimation(DRAGON_ANIM_SCRATCH);  npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_BLOCK.contains(current))     { npc.setAnimation(DRAGON_ANIM_BLOCK);    npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_HEADBUTT.contains(current))  { npc.setAnimation(DRAGON_ANIM_HEADBUTT); npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_FIRE.contains(current))      { npc.setAnimation(DRAGON_ANIM_FIRE);     npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_DEATH.contains(current))     { npc.setAnimation(DRAGON_ANIM_DEATH);    npc.setAnimationFrame(0); }
		}
		if (config.legacyBlueDragon() && (BLUE_DRAGON_SOURCE_IDS.contains(id) || id == BLUE_DRAGON_TARGET_ID)) {
			if (DRAGON_SRC_SCRATCH.contains(current))        { npc.setAnimation(DRAGON_ANIM_SCRATCH);  npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_BLOCK.contains(current))     { npc.setAnimation(DRAGON_ANIM_BLOCK);    npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_HEADBUTT.contains(current))  { npc.setAnimation(DRAGON_ANIM_HEADBUTT); npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_FIRE.contains(current))      { npc.setAnimation(DRAGON_ANIM_FIRE);     npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_DEATH.contains(current))     { npc.setAnimation(DRAGON_ANIM_DEATH);    npc.setAnimationFrame(0); }
		}
		if (config.legacyBabyBlueDragon() && (BABY_BLUE_DRAGON_SOURCE_IDS.contains(id) || id == BABY_BLUE_DRAGON_TARGET_ID)) {
			if (BABY_BLUE_DRAGON_SRC_ATTACK.contains(current))     { npc.setAnimation(BABY_BLUE_DRAGON_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (BABY_BLUE_DRAGON_SRC_BLOCK.contains(current)) { npc.setAnimation(BABY_BLUE_DRAGON_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (BABY_BLUE_DRAGON_SRC_DEATH.contains(current)) { npc.setAnimation(BABY_BLUE_DRAGON_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyScorpion() && (SCORPION_SOURCE_IDS.contains(id) || id == SCORPION_TARGET_ID)) {
			if (SCORPION_SRC_ATTACK.contains(current))     { npc.setAnimation(SCORPION_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (SCORPION_SRC_BLOCK.contains(current)) { npc.setAnimation(SCORPION_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (SCORPION_SRC_DEATH.contains(current)) { npc.setAnimation(SCORPION_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyCaveBug() && (CAVE_BUG_SOURCE_IDS.contains(id) || id == CAVE_BUG_TARGET_ID)) {
			if (CAVE_BUG_SRC_ATTACK.contains(current))     { npc.setAnimation(CAVE_BUG_ANIM_ATTACK); npc.setAnimationFrame(0); }
			else if (CAVE_BUG_SRC_BLOCK.contains(current)) { npc.setAnimation(CAVE_BUG_ANIM_BLOCK);  npc.setAnimationFrame(0); }
			else if (CAVE_BUG_SRC_DEATH.contains(current)) { npc.setAnimation(CAVE_BUG_ANIM_DEATH);  npc.setAnimationFrame(0); }
		}
		if (config.legacyGreenDragon() && GREEN_DRAGON_SOURCE_IDS.contains(id)) {
			if (DRAGON_SRC_SCRATCH.contains(current))        { npc.setAnimation(DRAGON_ANIM_SCRATCH);  npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_BLOCK.contains(current))     { npc.setAnimation(DRAGON_ANIM_BLOCK);    npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_HEADBUTT.contains(current))  { npc.setAnimation(DRAGON_ANIM_HEADBUTT); npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_FIRE.contains(current))      { npc.setAnimation(DRAGON_ANIM_FIRE);     npc.setAnimationFrame(0); }
			else if (DRAGON_SRC_DEATH.contains(current))     { npc.setAnimation(DRAGON_ANIM_DEATH);    npc.setAnimationFrame(0); }
		}
		if (config.legacyKingBlackDragon() && (KING_BLACK_DRAGON_SOURCE_IDS.contains(id) || id == KING_BLACK_DRAGON_TARGET_ID)) {
			if (KING_BLACK_DRAGON_SRC_SHOCK.contains(current))         { npc.setAnimation(KING_BLACK_DRAGON_ANIM_SHOCK);   npc.setAnimationFrame(0); }
			else if (KING_BLACK_DRAGON_SRC_ICE.contains(current))      { npc.setAnimation(KING_BLACK_DRAGON_ANIM_ICE);     npc.setAnimationFrame(0); }
			else if (KING_BLACK_DRAGON_SRC_POISON.contains(current))   { npc.setAnimation(KING_BLACK_DRAGON_ANIM_POISON);  npc.setAnimationFrame(0); }
			else if (KING_BLACK_DRAGON_SRC_BREATH.contains(current))   { npc.setAnimation(KING_BLACK_DRAGON_ANIM_BREATH);  npc.setAnimationFrame(0); }
			else if (KING_BLACK_DRAGON_SRC_SCRATCH.contains(current))  { npc.setAnimation(KING_BLACK_DRAGON_ANIM_SCRATCH);  npc.setAnimationFrame(0); }
			else if (KING_BLACK_DRAGON_SRC_HEADBUTT.contains(current)) { npc.setAnimation(KING_BLACK_DRAGON_ANIM_HEADBUTT); npc.setAnimationFrame(0); }
			else if (KING_BLACK_DRAGON_SRC_BLOCK.contains(current))    { npc.setAnimation(KING_BLACK_DRAGON_ANIM_BLOCK);   npc.setAnimationFrame(0); }
			else if (KING_BLACK_DRAGON_SRC_DEATH.contains(current))    { npc.setAnimation(KING_BLACK_DRAGON_ANIM_DEATH);   npc.setAnimationFrame(0); }
		}
	}

	private Field findFieldByValue(Object obj, Object value) {
		if (value == null) return null;
		Class<?> clazz = obj.getClass();
		while (clazz != null && clazz != Object.class) {
			for (Field f : clazz.getDeclaredFields()) {
				f.setAccessible(true);
				try {
					if (f.get(obj) == value) return f;
				} catch (Exception ignored) {}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	@Provides
	OlderSchoolConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(OlderSchoolConfig.class);
	}
}