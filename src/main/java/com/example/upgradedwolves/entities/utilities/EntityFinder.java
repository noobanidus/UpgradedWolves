package com.example.upgradedwolves.entities.utilities;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.MathHelper;

public class EntityFinder<T extends Entity> {
    public final MobEntity entityOwner;
    private final Class<T> entityType;

    public EntityFinder(MobEntity entityOwner, Class<T> entityType){
        this.entityOwner = entityOwner;
        this.entityType = entityType;
    }

    public List<T> findWithinRange(double xz, double y){
        return entityOwner.world.getEntitiesWithinAABB(entityType, entityOwner.getBoundingBox().grow(xz, y, xz));
    }

    public List<T> findReachableEntities(double xz, double y){
        List<T> retList = findWithinRange(xz, y);
        for (T t : retList) {
            if(!canEasilyReach(t)){
                retList.remove(t);
            }
        }
        return retList;
    }

    public List<T> findWithPredicate(double xz, double y, Predicate<T> predicate ){
        List<T> retList = findWithinRange(xz, y);
        for (T t : retList) {
            if(!predicate.test(t))
                retList.remove(t);
        }
        return retList;
    }

    public boolean entityInRange(T entity, double range){
        double distance = entityOwner.getDistance(entity);
        return distance < range && canEasilyReach(entity);
    }

    private boolean canEasilyReach(T target) {        
        Path path = entityOwner.getNavigator().getPathToEntity(target, 0);
        if (path == null) {
           return false;
        } else {
           PathPoint pathpoint = path.getFinalPathPoint();
           if (pathpoint == null) {
              return false;
           } else {
              int i = pathpoint.x - MathHelper.floor(target.getPosX());
              int j = pathpoint.z - MathHelper.floor(target.getPosZ());
              return (double)(i * i + j * j) <= 2.25D;
           }
        }
    }
}
