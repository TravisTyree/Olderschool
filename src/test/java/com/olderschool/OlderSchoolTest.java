package com.olderschool;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class OlderSchoolTest {
	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(OlderSchoolPlugin.class);
		RuneLite.main(args);
	}
}