package com.example.upgradedwolves.entities.utilities;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.world.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class EntityFinder<T extends Entity> {
    public final LivingEntity entityOwner;
    private final Class<T> entityType;

    public EntityFinder(LivingEntity entityOwner, Class<T> entityType){
        this.entityOwner = entityOwner;
        this.entityType = entityType;
    }

    public List<T> findWithinRange(double xz, double y){
        return entityOwner.world.getEntitiesWithinAABB(entityType, entityOwner.getBoundingBox().grow(xz, y, xz));
    }

    public List<T> findWithPredicate(double xz, double y, Predicate<T> predicate ){
        List<T> retList = findWithinRange(xz, y);
        retList.removeIf(predicate.negate());        
        return retList;
    }
}
