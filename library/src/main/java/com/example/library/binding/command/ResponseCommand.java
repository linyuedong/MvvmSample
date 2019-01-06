package com.example.library.binding.command;

public class ResponseCommand<T, R> {

    private BindFunc0<R> execute0;
    private BindFunc1<T, R> execute1;

    private BindFunc0<Boolean> canExecute0;

    /**
     * like {@link ReplyCommand},but ResponseCommand can return result when command has executed!
     * @param execute function to execute when event occur.
     */
    public ResponseCommand(BindFunc0<R> execute) {
        this.execute0 = execute;
    }


    public ResponseCommand(BindFunc1<T, R> execute) {
        this.execute1 = execute;
    }


    public ResponseCommand(BindFunc0<R> execute, BindFunc0<Boolean> canExecute0) {
        this.execute0 = execute;
        this.canExecute0 = canExecute0;
    }


    public ResponseCommand(BindFunc1<T, R> execute, BindFunc0<Boolean> canExecute0) {
        this.execute1 = execute;
        this.canExecute0 = canExecute0;
    }


    public R execute() {
        if (execute0 != null && canExecute0()) {
            return execute0.call();
        }
        return null;
    }

    private boolean canExecute0() {
        if (canExecute0 == null) {
            return true;
        }
        return canExecute0.call();
    }


    public R execute(T parameter) {
        if (execute1 != null && canExecute0()) {
            return execute1.call(parameter);
        }
        return null;
    }

}
