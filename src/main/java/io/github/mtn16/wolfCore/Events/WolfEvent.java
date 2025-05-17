package io.github.mtn16.wolfCore.Events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class WolfEvent<T extends Event> {

    private final Class<T> eventClass;
    private final Consumer<T> handler;
    private final Plugin plugin;

    private EventPriority priority = EventPriority.NORMAL;
    private boolean ignoreCancelled = false;
    private boolean log = false;

    public WolfEvent(Class<T> eventClass, Consumer<T> handler, Plugin plugin) {
        this.eventClass = eventClass;
        this.handler = handler;
        this.plugin = plugin;
    }

    public WolfEvent<T> priority(EventPriority priority) {
        this.priority = priority;
        return this;
    }

    public WolfEvent<T> ignoreCancelled(boolean ignore) {
        this.ignoreCancelled = ignore;
        return this;
    }

    public WolfEvent<T> log(boolean log) {
        this.log = log;
        return this;
    }

    public Class<T> getEventClass() { return eventClass; }
    public Consumer<T> getHandler() { return handler; }
    public Plugin getPlugin() { return plugin; }
    public EventPriority getPriority() { return priority; }
    public boolean shouldIgnoreCancelled() { return ignoreCancelled; }
    public boolean shouldLog() { return log; }
}
