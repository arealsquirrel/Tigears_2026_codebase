package com.tigears.finitestateautomata;

public class SingletonViolationException extends RuntimeException {
    public SingletonViolationException() { super(); }
    public SingletonViolationException(String s) { super("Not allowed to instantiate a %s since it is a singleton.".formatted(s)); }
    public SingletonViolationException(Object o) { this(o.getClass().getName()); }

}

public class FiniteStateAutomata {
    //enforce singleton
    private FiniteStateAutomata() { 
        if (Holder.FSA != null) throw new SingletonViolationException(this)); 

    }
    private static class Holder { 
        private static final FiniteStateAutomata FSA = new FiniteStateAutomata(); 
        private static State = ;
    }
    //getter for the fsa
    public FiniteStateAutomata FSA() { return Holder.FSA; }
}

public static class StateFactory {
    
}

public static class StatePool {
    private StatePool() {
        if (Holder.StatePool != null) throw new RuntimeException(this);
    }
    private static class Holder {
        private static final StatePool STATE_POOL = new StatePool();
    }
    private static final ArrayList<State> states = new ArrayList<State>();
}

private class State {

}

public static class EdgeFactory {

}

private class Edge {

}
