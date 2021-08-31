package com.example.upgradedwolves.entities.goals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Describes a goal that needs to be accessed used by the client.
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ClientGoal {

}
