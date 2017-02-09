package org.ayo.bug;


/**
 * A callback to be run before an individual report is sent to Bugsnag.
 *
 * Use this to add or modify error report information before it is sent to
 * Bugsnag.
 *
 * @see Bugsnag#notify(Throwable,Callback)
 */
public interface Callback {

    public abstract void beforeNotify(Report report);
}
