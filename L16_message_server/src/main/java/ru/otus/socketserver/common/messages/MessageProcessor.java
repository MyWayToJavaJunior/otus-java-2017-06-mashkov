package ru.otus.socketserver.common.messages;

import java.util.Optional;

/**
 * @autor slonikmak on 12.10.2017.
 */
public interface MessageProcessor {
    Optional<Msg> process(Msg msg);
}
