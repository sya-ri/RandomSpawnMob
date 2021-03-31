package com.github.syari.yululi.randomspawnmob

import com.github.syari.spigot.api.event.EventRegister
import com.github.syari.spigot.api.event.Events
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.CreatureSpawnEvent

object EventListener : EventRegister {
    private val overrideSpawnEntity = mutableSetOf<Location>()
    private val randomSpawnEntityType = EntityType.values().filter { it.isAlive && it != EntityType.PLAYER }

    override fun Events.register() {
        event<CreatureSpawnEvent> {
            if (overrideSpawnEntity.remove(it.location).not()) {
                it.isCancelled = true
                overrideSpawnEntity.add(it.location)
                it.entity.world.spawnEntity(it.location, randomSpawnEntityType.random())
            }
        }
    }
}
