package ru.otus.messageserver.messages.frontend;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;
import ru.otus.messageserver.messages.app.FrontendService;

/**
 * @autor slonikmak on 27.09.2017.
 */
public abstract class MsgToFrontend extends Message {


    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}
