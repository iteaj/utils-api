package com.iteaj.util.module.authorization;

import java.util.HashMap;
import java.util.Map;

/**
 * Create Date By 2018-03-07
 *
 * @author iteaj
 * @since 1.7
 */
public class MapStorageContext implements AuthorizeContext {

    private Map storage;
    private AuthorizePhase tickPhase;
    private AuthorizationType type;
    private AuthorizeActionAbstract actionAbstract;

    public MapStorageContext() {
        storage = new HashMap();
    }

    public MapStorageContext(Map storage) {
        this.storage = storage;
    }

    @Override
    public void init(AuthorizationType type, AuthorizeActionAbstract action) {
        this.type = type;
        this.tickPhase = type.getAuthorizePhase(type.getPhaseEntry());
        this.actionAbstract = action;
        actionAbstract.setInvokePhase("token");
    }

    @Override
    public void release() {
        /*NOOP*/
    }

    @Override
    public AuthorizePhase getTickPhase() {
        AuthorizePhase tempPhase = tickPhase;
        if(null == tempPhase) return tempPhase;

        tickPhase = tempPhase.nextPhase();
        return tempPhase;
    }

    @Override
    public Object getParam(String name) {
        return storage.get(name);
    }

    @Override
    public AuthorizeContext addParam(String name, Object value) {
        storage.put(name, value);
        return this;
    }

    @Override
    public AuthorizationType getType() {
        return type;
    }

    @Override
    public AuthorizeActionAbstract getAuthorizeAction() {
        return actionAbstract;
    }
}
