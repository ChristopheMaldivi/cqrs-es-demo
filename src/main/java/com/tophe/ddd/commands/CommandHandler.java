package com.tophe.ddd.commands;

import com.tophe.ddd.bus.BusHandler;

public abstract class CommandHandler<T extends Command, R> extends BusHandler<T, R, CommandResponse<R>> {
}
