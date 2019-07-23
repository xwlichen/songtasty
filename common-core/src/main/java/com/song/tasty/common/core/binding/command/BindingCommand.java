package com.song.tasty.common.core.binding.command;

/**
 * @date : 2019-07-23 17:01
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class BindingCommand<T> {
    private BindingAction execute;
    private BindingConsumer<T> consumer;
    private BindingFunction<Boolean> canExecute;


    public BindingCommand(BindingAction execute) {
        this.execute = execute;
    }

    /**
     * @param execute 带泛型参数的命令绑定
     */
    public BindingCommand(BindingConsumer<T> execute) {
        this.consumer = execute;
    }


    /**
     * @param execute    带泛型参数触发命令
     * @param canExecute true则执行,反之不执行
     */
    public BindingCommand(BindingConsumer<T> execute, BindingFunction<Boolean> canExecute) {
        this.consumer = execute;
        this.canExecute = canExecute;
    }

    /**
     * 执行BindingAction命令
     */
    public void execute() {
        if (execute != null && canExecute()) {
            execute.call();
        }
    }

    /**
     * 执行带泛型参数的命令
     *
     * @param parameter 泛型参数
     */
    public void execute(T parameter) {
        if (consumer != null && canExecute()) {
            consumer.call(parameter);
        }
    }

    /**
     * 是否需要执行
     *
     * @return true则执行, 反之不执行
     */
    private boolean canExecute() {
        if (canExecute == null) {
            return true;
        }
        return canExecute.call();
    }
}
