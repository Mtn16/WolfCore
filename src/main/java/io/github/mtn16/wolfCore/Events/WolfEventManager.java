package io.github.mtn16.wolfCore.Events;

import org.bukkit.event.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class WolfEventManager {

    private static final Map<Plugin, List<RegisteredListener>> registered = new ConcurrentHashMap<>();

    public static <T extends Event> void on(Class<T> eventClass, Plugin plugin, Consumer<T> handler) {
        registerEvent(new WolfEvent<>(eventClass, handler, plugin));
    }

    public static <T extends Event> void registerEvent(WolfEvent<T> dynamicEvent) {
        Plugin plugin = dynamicEvent.getPlugin();
        Listener listener = new Listener() {}; // Anonymní posluchač, nepoužívá se přímo

        RegisteredListener wrapped = new RegisteredListener(listener, (listener1, event) -> {
            if (dynamicEvent.getEventClass().isInstance(event)) {
                T typedEvent = dynamicEvent.getEventClass().cast(event);

                if (event instanceof Cancellable && dynamicEvent.shouldIgnoreCancelled() && ((Cancellable) event).isCancelled()) {
                    return;
                }

                if (dynamicEvent.shouldLog()) {
                    plugin.getLogger().info("[WolfEvent] " + event.getEventName() + " triggered.");
                }

                dynamicEvent.getHandler().accept(typedEvent);
            }
        }, dynamicEvent.getPriority(), plugin, false);

        try {
            Method getHandlerList = dynamicEvent.getEventClass().getMethod("getHandlerList");
            HandlerList handlerList = (HandlerList) getHandlerList.invoke(null);
            handlerList.register(wrapped);

            registered.computeIfAbsent(plugin, k -> new ArrayList<>()).add(wrapped);

        } catch (Exception e) {
            plugin.getLogger().severe("An error occurred while attempting to register event: " + dynamicEvent.getEventClass().getSimpleName());
            e.printStackTrace();
        }
    }

    public static void unregisterAll(Plugin plugin) {
        List<RegisteredListener> listeners = registered.remove(plugin);
        if (listeners == null) return;

        for (RegisteredListener listener : listeners) {
            HandlerList.unregisterAll(listener.getListener());
        }
    }

    public static void unregisterAll() {
        for (Plugin plugin : registered.keySet()) {
            unregisterAll(plugin);
        }
        registered.clear();
    }
}