package com.tophe.ddd.infrastructure.commands;

import com.tophe.ddd.infrastructure.bus.BusHandler;

public abstract class CommandHandler<T extends Command, R> extends BusHandler<T, R, CommandResponse<R>> {
}
