package com.iteaj.util.module.aop.output;

import com.iteaj.util.module.aop.ActionOutput;
import com.iteaj.util.module.aop.ActionRecord;

/**
 * Create Date By 2018-03-19
 *
 * @author iteaj
 * @since 1.7
 */
public class VoidOutput extends ActionOutput {

    public static VoidOutput VOID_OUT_PUT = new VoidOutput();

    @Override
    public void write(ActionRecord record) throws Exception {
        /*doing nothing*/
    }

    @Override
    public boolean isMatching(ActionRecord record) {
        return false;
    }

}
