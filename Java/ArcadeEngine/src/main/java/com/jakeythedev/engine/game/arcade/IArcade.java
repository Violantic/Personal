package com.jakeythedev.engine.game.arcade;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jakeythedev.engine.game.GameTypes;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IArcade
{

	String gameName();
	String[] gameDescription();
	GameTypes gameTypes();
	int minimumPlayers();
}
