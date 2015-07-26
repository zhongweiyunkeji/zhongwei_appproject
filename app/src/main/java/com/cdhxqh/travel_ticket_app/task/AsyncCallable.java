package com.cdhxqh.travel_ticket_app.task;



public interface AsyncCallable<T> {
	public void call(final Callback<T> pCallback, final Callback<Exception> pExceptionCallback);
}
