package com.sen.concurrency2.chapter18;


/**
 * @Auther: Sen
 * @Date: 2019/12/11 16:24
 * @Description:
 */
public class DisplayStringRequest extends MethodResult {

    private final String text;

    public DisplayStringRequest(Servant servant, String text) {
        super(servant,null);
        this.text = text;
    }

    @Override
    public void execute() {
        servant.displayString(text);
    }
}
