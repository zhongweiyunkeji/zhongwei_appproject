package com.cdhxqh.travel_ticket_app.task;



public interface ProgressCallable<T> {
    public T call(final IProgressListener pProgressListener) throws Exception;
}
